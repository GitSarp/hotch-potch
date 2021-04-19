package com.example.dynamicdatasource.interceptor;

import com.example.dynamicdatasource.context.JdbcContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: freaxjj
 * @Desc: 拦截器拦截租户信息，并设置选择数据源的key
 * @Date: 4/15/21 5:55 PM
 */
@Slf4j
public class DataSourceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try{
            String tenant = request.getHeader("tenantId");
            if(StringUtils.isEmpty((tenant))){
                tenant = request.getParameter("tenantId");
            }
            if (StringUtils.hasText(tenant)) {
                log.info("当前租户: {}", tenant);
                JdbcContextHolder.setCurrentTenant(tenant);
            } else {
                log.info("使用默认租户...");
                JdbcContextHolder.setDefaultTenant();
            }
        }catch (Exception e){
            JdbcContextHolder.setDefaultTenant();
        }
        return true;
    }
}
