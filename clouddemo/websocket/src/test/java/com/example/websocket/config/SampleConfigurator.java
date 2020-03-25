package com.example.websocket.config;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.HandshakeResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 刘亚林
 * @description websocket客户端配置
 * @create 2020/3/25 14:42
 **/
public class SampleConfigurator extends ClientEndpointConfig.Configurator {

    /**
     * 操作websocket header头信息
     * @param headers
     */
    @Override
    public void beforeRequest(Map<String, List<String>> headers) {
        //Auto-generated method stub
        //添加头信息
        headers.put("token", Arrays.asList("token_test"));
        System.out.println(headers);
    }

    /**
     * 处理返回响应
     * @param handshakeResponse
     */
    @Override
    public void afterResponse(HandshakeResponse handshakeResponse) {
        //Auto-generated method stub
        System.out.println(handshakeResponse);
    }

}
