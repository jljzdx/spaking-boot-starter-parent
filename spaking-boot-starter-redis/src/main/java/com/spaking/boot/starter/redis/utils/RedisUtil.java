package com.spaking.boot.starter.redis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;

@Component
@Slf4j
public class RedisUtil {

    private final Long RELEASE_SUCCESS = 1L;
    private final String LOCK_SCRIPT = "if redis.call('set',KEYS[1],ARGV[1],'NX','PX',ARGV[2]) then return 1 else return 0 end";
    private final String UNLOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private DefaultRedisScript<Long> lockScript;
    private DefaultRedisScript<Long> unlockScript;
    private RedisSerializer argsSerializer;
    private RedisSerializer resultSerializer;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void init() {
        //序列化器
        argsSerializer = new StringRedisSerializer();
        resultSerializer = new StringRedisSerializer();
        //LUA
        lockScript = new DefaultRedisScript();
        lockScript.setScriptText(LOCK_SCRIPT);
        lockScript.setResultType(Long.class);
        unlockScript = new DefaultRedisScript();
        unlockScript.setScriptText(UNLOCK_SCRIPT);
        unlockScript.setResultType(Long.class);
    }
    /**
     * 获取
     * @param key
     * @param <T>
     * @return
     */
    public <T> T get(String key){
        Object object = redisTemplate.opsForValue().get(key);
        if(null == object){
            return null;
        }
        return (T)object;
    }
    /**
     * 存储：不设置过期时间
     * @param key
     * @param value
     * @return
     */
    public Boolean set(String key, Object value){
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key,value);
        return result;
    }
    /**
     * 存储
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public void set(String key, Object value, Long seconds){
        if(null == seconds){
            redisTemplate.opsForValue().set(key,value);
        }else{
            Duration duration = Duration.ofSeconds(seconds);
            redisTemplate.opsForValue().set(key,value,duration);
        }
    }

    /**
     * 删除
     * @param key
     * @return
     */
    public Boolean delete(String key){
        Boolean result = redisTemplate.delete(key);
        return result;
    }

    /**
     * 递增：计数器
     * @param key
     * @return
     */
    public Long increase(String key){
        Long result = redisTemplate.opsForValue().increment(key);
        return result;
    }

    /**
     * 分布式锁：获得锁
     * @param key
     * @param value
     * @param milliseconds
     * @return
     */
    public Boolean tryLock(String key, Object value, Long milliseconds){
        Object object = redisTemplate.execute(lockScript, argsSerializer, resultSerializer, Collections.singletonList(key),value,milliseconds.toString());
        Long result = (Long)object;
        log.info("获得锁结果 = [{}]",result);
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 分布式锁：释放锁
     * @param key
     * @param value
     * @return
     */
    public Boolean releaseLock(String key, Object value){
        Object object = redisTemplate.execute(unlockScript, argsSerializer, resultSerializer, Collections.singletonList(key),value);
        Long result = (Long)object;
        log.info("释放锁结果 = [{}]",result);
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

}
