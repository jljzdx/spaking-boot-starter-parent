package com.spaking.boot.starter.core.aop;

import com.spaking.boot.starter.core.annotation.AvoidRepeatSubmit;
import com.spaking.boot.starter.core.exception.BaseException;
import com.spaking.boot.starter.redis.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 重复提交场景：
 * 1、在网络延迟的情况下让用户有时间点击多次submit按钮导致表单重复提交
 * 2、表单提交后用户点击【刷新】按钮导致表单重复提交
 * 3、用户提交表单后，点击浏览器的【后退】按钮回退到表单页面后进行再次提交
 */
@Aspect
@Component
@Slf4j
@Order(value = 1)
public class RepeatSubmitAop {

    @Autowired
    private RedisUtil redisUtil;

    @Pointcut("@annotation(com.spaking.boot.starter.core.annotation.AvoidRepeatSubmit)")
    public void cutRepeatSubmit(){}

    @Around("cutRepeatSubmit()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        //获取自定义注解
        AvoidRepeatSubmit annotation =  method.getAnnotation(AvoidRepeatSubmit.class);
        String path = request.getServletPath();//如：/cms/admin/role/addition
        String token = request.getHeader("Authorization");
        //key-value
        String key = token + path;
        String value = UUID.randomUUID().toString();
        Long seconds = annotation.timeout();
        log.info("key = [{}], value = [{}], timeout = [{}]",key,value,seconds);
        //获得锁
        Boolean isSuccess = redisUtil.tryLock(key, value, seconds);
        if (isSuccess) {
            log.info("tryLock success, key = [{}], value = [{}]", key, value);
            Object obj;
            try {
                //执行方法
                obj = joinPoint.proceed();
            } finally {
                //释放锁
                redisUtil.releaseLock(key, value);
                log.info("releaseLock success, key = [{}], clientId = [{}]", key, value);
            }
            return obj;
        } else {
            //获得锁失败，认为是重复提交的请求
            log.info("tryLock failed, key = [{}]", key);
            throw new BaseException("重复请求，请稍后再试","222");
        }
    }
}
