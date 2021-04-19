package com.example.dynamicdatasource.aop;

import com.example.dynamicdatasource.context.JdbcContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/15/21 3:55 PM
 */
@Aspect
@Component
// 请注意：这里order一定要小于tx:annotation-driven的order，即先执行DynamicDataSourceAspectAdvice切面，再执行事务切面，才能获取到最终的数据源
@Order(0)
@Slf4j
public class DynamicDataSourceAspect {
    //@Around("execution(* com.example.dynamicdatasource.controller.*.*(..)) || execution(* com.example.dynamicdatasource.*.*(..))")
    @Around("@annotation(com.example.dynamicdatasource.annotation.Tenant)")
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object result;
        try {
            HttpServletRequest request = sra.getRequest();
            String tenantId = request.getHeader("tenantId");
            if (StringUtils.isEmpty(tenantId)) {
                tenantId = request.getParameter("tenantId");
            }
            if (!StringUtils.isEmpty(tenantId)) {
                log.info("当前租户Id:{}", tenantId);
                JdbcContextHolder.setCurrentTenant(tenantId);
            } else {
                log.info("使用默认租户！");
                JdbcContextHolder.setDefaultTenant();
            }
            result = jp.proceed();
        } catch (Exception ex) {
            ex.printStackTrace();
            result = "系统异常，请联系技术专家！";
        } finally {
            JdbcContextHolder.clearDataSourceKey();
        }
        return result;
    }
}
