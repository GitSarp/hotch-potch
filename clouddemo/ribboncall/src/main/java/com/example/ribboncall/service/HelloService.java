package com.example.ribboncall.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {
    @Autowired
    RestTemplate restTemplate;

    //调用cloudclient微服务
    @HystrixCommand(fallbackMethod = "hiError")//断路器
    public String callDC(String name){
        return restTemplate.getForObject("http://eureka-client/dc?name="+name,String.class);//eureka-client为微服务的名称
    }

    public String hiError(String name){
        return "cloudclient goes wrong,freaxjj return this as fallback";
    }

}
