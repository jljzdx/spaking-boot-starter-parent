package com.spaking.boot.starter.cas.utils;

import com.spaking.boot.starter.cas.model.SsoUser;
import com.spaking.boot.starter.redis.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SsoLoginStore {

    private static RedisUtil redisUtil;

    @Autowired
    public SsoLoginStore(RedisUtil redisUtil) {
        SsoLoginStore.redisUtil = redisUtil;
    }
    private static int redisExpireMinite = 24*60;    // 1440 minite, 24 hour

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
        SsoUser ssoUser = redisUtil.get(redisKey);
        log.info("SsoLoginStore get："+ssoUser);
        return ssoUser;
    }

    /**
     * remove
     *
     * @param storeKey
     */
    public static void remove(String storeKey) {
        String redisKey = redisKey(storeKey);
        Boolean result = redisUtil.delete(redisKey);
        log.info("SsoLoginStore remove："+result);
    }

    /**
     * put
     *
     * @param storeKey
     * @param ssoUser
     */
    public static void put(String storeKey, SsoUser ssoUser) {
        String redisKey = redisKey(storeKey);
        Boolean result = redisUtil.setx(redisKey,ssoUser,redisExpireMinite*60L);
        log.info("SsoLoginStore put："+result);
    }

    private static String redisKey(String sessionId){
        return SsoConstant.SSO_SESSIONID.concat("#").concat(sessionId);
    }

}
