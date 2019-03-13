package com.example.boottaskdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BoottaskdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoottaskdemoApplication.class, args);
    }

}
