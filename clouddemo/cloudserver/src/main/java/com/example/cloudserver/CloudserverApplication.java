package com.example.cloudserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer//服务注册中心
@SpringBootApplication
public class CloudserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudserverApplication.class, args);
    }
}
