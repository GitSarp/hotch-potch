package com.example.ribboncall.controller;

import com.example.ribboncall.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    HelloService helloService;

    @GetMapping(value = "/hi")
    public String sayHello(@RequestParam(value = "name")String name){
        return helloService.callDC(name);
    }
}
