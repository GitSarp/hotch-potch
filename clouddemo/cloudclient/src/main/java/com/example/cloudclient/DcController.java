package com.example.cloudclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DcController {
    @Autowired
    DiscoveryClient discoveryClient;//发现服务
    @Autowired
    UserFeignClient userFeignClient;//调用其他微服务

    @Value("${server.port}")
    String port;//配置文件获取配置

    //本地服务，获取所有服务和服务端口
    @GetMapping("/dc")
    public String getServices(@RequestParam(value = "name",defaultValue = "service1")String name){
        String retStr="services:"+discoveryClient.getServices()+";name:"+name+"\n from cloudclient:"+port;
        System.out.println(retStr);
        return retStr;
    }

    //调用ms3服务
    @RequestMapping("/getUser")
    public String getUser() {
        return "Admin：" + userFeignClient.getUser();
    }
}
