package com.example.websocket.config;

import com.example.websocket.controller.WebSocketEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * @author 刘亚林
 * @description
 * @create 2019/10/22 16:40
 **/
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketEngine(), "/bullet2")
                //.addInterceptors(new HandShakeInterceptor())
                //允许跨域
                .setAllowedOrigins("*");
    }

    /**
     * web container config
     * 录音笔服务端  spring websocket配置
     * @return
     */
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        //设置会话时间40s,不设置为null，录音笔ws连接不会自动断开
        container.setMaxSessionIdleTimeout(40000L);
        //container.setMaxTextMessageBufferSize(8192);
        //container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }
}
