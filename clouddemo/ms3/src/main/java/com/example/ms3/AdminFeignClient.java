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
@FeignClient(value = "eureka-client", fallback = AdminFallback.class)//调用cloudclient(对应的应用名是eureka-client),当服务不可用时，调用AdminFallback
public interface AdminFeignClient {
    @GetMapping("/dc")
    String getServices();
}
