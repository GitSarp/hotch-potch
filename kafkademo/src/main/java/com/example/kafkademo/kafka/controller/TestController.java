package com.example.kafkademo.kafka.controller;

import com.example.kafkademo.kafka.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 5:47 PM 2019/3/4
 * @ Description：
 * @ Modified By：
 */
@RestController
public class TestController {
    @Autowired
    KafkaTemplate kafkaTemplate;

    @GetMapping("/test")
    public String test(){
        for (int i = 0; i < 10; i++) {
            kafkaTemplate.send("test","message"+i);
        }
        return "success";
    }

    @GetMapping("/test12")
    public String test12(){
        for (int i = 0; i < 10; i++) {
            kafkaTemplate.send("test12",new TestModel(i,"name-"+i));
        }
        return "success";
    }
}
