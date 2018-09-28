package com.example.gatewaydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy//开启zuul实现路由转发
@EnableEurekaClient
@EnableDiscoveryClient
public class GatewaydemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewaydemoApplication.class, args);
    }
}
