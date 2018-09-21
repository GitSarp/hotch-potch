package com.example.cloudclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 说明。
 *
 * @author
 * @date
 */
@FeignClient("ms3")//调用微服务ms3
public interface UserFeignClient {
    @RequestMapping(value = "/getUser")
    String getUser();
}
