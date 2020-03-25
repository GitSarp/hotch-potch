package com.example.websocket.config;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author 刘亚林
 * @description
 * @create 2020/3/25 14:47
 **/
public class SimpleEncoder implements Encoder.Text<String> {
    @Override
    public String encode(String s) throws EncodeException {
        System.out.println("encode: " + s);
        return "mess:" + s.toUpperCase();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        System.out.println("Encoder init: " + endpointConfig.getUserProperties());
    }

    @Override
    public void destroy() {

    }
}
