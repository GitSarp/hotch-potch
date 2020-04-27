package com.example.gatewaydemo.feigh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 刘亚林
 * @description
 * @create 2020/4/8 11:15
 **/
@Component
public class TestFeignFallBack implements TestlFeignClient{
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public String getHealthInfo() {
        logger.info("健康检查服务熔断异常");
        return "健康检查服务熔断异常";
    }

    @Override
    public String closeWebsocket(String groupId) {
        logger.info("关闭连接熔断异常");
        return "关闭连接熔断异常";
    }
}