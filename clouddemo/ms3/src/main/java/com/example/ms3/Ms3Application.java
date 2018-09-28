package com.example.ms3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients//调用其他微服务
@EnableHystrix//熔断器
@EnableHystrixDashboard//熔断监控
@EnableCircuitBreaker//熔断器
@SpringBootApplication
public class Ms3Application {
    public static void main(String[] args) {
        SpringApplication.run(Ms3Application.class, args);
    }
}
