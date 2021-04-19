package com.example.dynamicdatasource.interceptor;

import com.example.dynamicdatasource.context.JdbcContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: freaxjj
 * @Desc: feign传递租户信息
 * @Date: 4/16/21 9:30 AM
 */

@Component
public class FeignTenantInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        String tenant = JdbcContextHolder.getCurrentTenant();
        template.header("tenant",tenant);
    }
}
