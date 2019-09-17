package com.spaking.boot.starter.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class RedisUtil {

    @Autowired
    private ReactiveRedisTemplate redisTemplate;

    /**
     * 获取
     * @param key
     * @param <T>
     * @return
     */
    public <T> T get(String key){
        Mono<Object> mono = redisTemplate.opsForValue().get(key);
        Object object = mono.block();
        if(null == object){
            return null;
        }
        return (T)object;
    }
    /**
     * 存储
     * @param key
     * @param object
     * @param seconds
     * @return
     */
    public Boolean setx(String key, Object object, Long seconds){
        Duration duration = Duration.ofSeconds(seconds);
        Mono<Boolean> mono = redisTemplate.opsForValue().set(key,object,duration);
        return mono.block();
    }

    /**
     * 删除
     * @param key
     * @return
     */
    public Boolean delete(String key){
        Mono<Boolean> mono = redisTemplate.opsForValue().delete(key);
        return mono.block();
    }

}
