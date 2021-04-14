package com.example.shirodemo.vo;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/12/21 2:02 PM
 */
@Component
public class JwtResultMap extends HashMap<String,Object> {

    public JwtResultMap success(){
        this.put("result","success");
        return this;
    }

    public JwtResultMap fail() {
        this.put("result", "fail");
        return this;
    }
    public JwtResultMap code(int code) {
        this.put("code", code);
        return this;
    }
    public JwtResultMap message(Object message) {
        this.put("message", message);
        return this;
    }
}
