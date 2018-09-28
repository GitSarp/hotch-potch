package com.example.ribboncall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableHystrix//断路器
@EnableHystrixDashboard//熔断监控
@EnableCircuitBreaker//断路器
@EnableTurbine//turbine熔断器
public class RibboncallApplication {

    public static void main(String[] args) {
        SpringApplication.run(RibboncallApplication.class, args);
    }

    @Bean//注入RestTemplate实例
    @LoadBalanced//手动开启负载均衡，而openfeign自带
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
