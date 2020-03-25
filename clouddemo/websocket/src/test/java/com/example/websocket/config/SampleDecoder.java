package com.example.websocket.config;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * @author 刘亚林
 * @description
 * @create 2020/3/25 14:46
 **/
public class SampleDecoder implements Decoder.Text<String> {
    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public String decode(String s) throws DecodeException {
        return "MESS:" + s.toLowerCase();
    }

    @Override
    public boolean willDecode(String s) {
        return false;
    }
}
