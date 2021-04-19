package com.example.dynamicdatasource.annotation;

import com.example.dynamicdatasource.context.JdbcContextHolder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/15/21 3:45 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Tenant {
    //String value() default JdbcContextHolder.DEFAULT_TENANT;
}
