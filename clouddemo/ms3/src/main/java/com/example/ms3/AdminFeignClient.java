package com.example.ms3;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 说明。
 *
 * @author
 * @date
 */
@FeignClient(value = "eureka-client", fallback = AdminFallback.class)//调用cloudclient
public interface AdminFeignClient {
    @GetMapping("/dc")
    String getServices();
}
