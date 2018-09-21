package com.example.ms3;

import org.springframework.stereotype.Component;

@Component
public class AdminFallback implements AdminFeignClient {
    @Override
    public String getServices() {
        return "AdminFallback";
    }
}
