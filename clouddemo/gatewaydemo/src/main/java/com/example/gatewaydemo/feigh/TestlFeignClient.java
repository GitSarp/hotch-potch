package com.example.gatewaydemo.feigh;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author 刘亚林
 * @description
 * @create 2020/4/8 11:15
 **/
@FeignClient(name = "ws-service",fallback = TestFeignFallBack.class)
public interface TestlFeignClient {

    /**
     * 获取服务健康状况
     * @return
     */
    @RequestLine("GET /actuator/info")
    //@GetMapping(value = "/actuator/info")
    String getHealthInfo();

    /**
     * 断开指定组的websocket连接
     * @return
     */
    @RequestLine("GET /close/{groupId}")
    String closeWebsocket(@Param("groupId") String groupId);
}
