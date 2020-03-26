package com.example.gatewaydemo.config;

import com.example.gatewaydemo.filter.WsUriHashFilter;
import com.example.gatewaydemo.filter.rule.CustomBalanceRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author 刘亚林
 * @description
 * @create 2020/3/26 12:27
 **/
@Configuration
public class LoadBalancedBean {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 注册负载均衡filter
     * @param client
     * @param properties
     * @return
     */
    @Bean
    public WsUriHashFilter userLoadBalanceClientFilter(LoadBalancerClient client, LoadBalancerProperties properties) {
        return new WsUriHashFilter(client, properties);
    }


    /**
     * 注册自定义负载均衡策略
     * @return
     */
    @Bean
    IRule rule(){
        return new CustomBalanceRule();
    }
}
