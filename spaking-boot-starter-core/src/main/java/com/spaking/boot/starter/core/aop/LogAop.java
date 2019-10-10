package com.spaking.boot.starter.core.aop;

import com.alibaba.fastjson.JSON;
import com.spaking.boot.starter.core.annotation.BusinessLogger;
import com.spaking.boot.starter.core.model.TransactionStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class LogAop {

    @Pointcut("@annotation(com.spaking.boot.starter.core.annotation.BusinessLogger)")
    public void cutPerm(){}

    @Around("cutPerm()")
    public Object Around(ProceedingJoinPoint joinPoint) throws Throwable{
        Object obj;
        long start = System.currentTimeMillis();
        //获取目标方法参数
        Object[] args = joinPoint.getArgs();
        if(args.length>0){
            Object object = args[0];
            //获取自定义注解
            MethodSignature ms = (MethodSignature) joinPoint.getSignature();
            BusinessLogger authority =  ms.getMethod().getAnnotation(BusinessLogger.class);
            log.info("["+authority.key()+"->"+authority.value()+"] RequestDTO=" + JSON.toJSONString(object));
            obj = joinPoint.proceed(args);
            Class<?> resultClz = obj.getClass();
            //父类
            Class<?> queryParentClz = resultClz.getSuperclass();
            //获取TransactionStatus
            Method method = queryParentClz.getDeclaredMethod("getTransactionStatus");
            TransactionStatus transactionStatus = (TransactionStatus)method.invoke(obj);
            long end = System.currentTimeMillis();
            transactionStatus.setDuration(end - start);
            Field field = queryParentClz.getDeclaredField("transactionStatus");
            field.setAccessible(true);
            field.set(obj,transactionStatus);
            log.info("["+authority.key()+"->"+authority.value()+"] ResponseDTO=" + JSON.toJSONString(obj));
        }else{
            obj = joinPoint.proceed();
        }
        return obj;
    }
}
