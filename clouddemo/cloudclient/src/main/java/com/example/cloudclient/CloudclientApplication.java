package com.example.cloudclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@RestController
@SpringBootApplication
public class CloudclientApplication {

    @Autowired
    DiscoveryClient discoveryClient;

    public static void main(String[] args) {
        SpringApplication.run(CloudclientApplication.class, args);
    }

    @GetMapping("/dc")
    public String getServices(){
        String retStr="services:"+discoveryClient.getServices();
        System.out.println(retStr);
        return retStr;
    }
}
