package com.spaking.boot.starter.cas.utils;

import com.spaking.boot.starter.cas.model.SsoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SsoLoginStore {

    private static RedisTemplate redisTemplate;

    @Autowired
    public SsoLoginStore(RedisTemplate redisTemplate) {
        SsoLoginStore.redisTemplate = redisTemplate;
    }
    private static int redisExpireMinite = 60*60;    // 1440 minite, 24 hour

    public static int getRedisExpireMinite() {
        return redisExpireMinite;
    }

    /**
     * get
     *
     * @param storeKey
     * @return
     */
    public static SsoUser get(String storeKey) {

        String redisKey = redisKey(storeKey);
        Object objectValue = redisTemplate.opsForValue().get(redisKey);
        if (objectValue != null) {
            SsoUser ssoUser = (SsoUser) objectValue;
            return ssoUser;
        }
        return null;
    }

    /**
     * remove
     *
     * @param storeKey
     */
    public static void remove(String storeKey) {
        String redisKey = redisKey(storeKey);
        redisTemplate.delete(redisKey);
    }

    /**
     * put
     *
     * @param storeKey
     * @param ssoUser
     */
    public static void put(String storeKey, SsoUser ssoUser) {
        String redisKey = redisKey(storeKey);
        redisTemplate.opsForValue().set(redisKey,ssoUser,redisExpireMinite, TimeUnit.SECONDS);
    }

    private static String redisKey(String sessionId){
        return SsoConstant.SSO_SESSIONID.concat("#").concat(sessionId);
    }

}
