package com.example.cloudclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DcController {
    @Autowired
    DiscoveryClient discoveryClient;//发现服务
    @Autowired
    UserFeignClient userFeignClient;//调用其他微服务

    @GetMapping("/dc")
    public String getServices(){
        String retStr="services:"+discoveryClient.getServices();
        System.out.println(retStr);
        return retStr;
    }

    @RequestMapping("/getUser")
    public String getUser() {
        return "Admin：" + userFeignClient.getUser();
    }
}
