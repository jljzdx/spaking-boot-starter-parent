package com.spaking.boot.starter.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AvoidRepeatSubmit {

    /**
     * 指定时间内不可重复提交,单位秒
     * @return
     */
    long timeout() default 10000;

}
