package com.example.dynamicdatasource.config;

import com.example.dynamicdatasource.interceptor.DataSourceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/16/21 9:28 AM
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new DataSourceInterceptor());
    }
}
