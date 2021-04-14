package com.example.shirodemo.controller;

import com.example.shirodemo.vo.JwtResultMap;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/12/21 2:03 PM
 */
@RestController
@RequestMapping("/guest")
public class GuestController {

    @Autowired
    private JwtResultMap resultMap;

    @GetMapping("/enter")
    public JwtResultMap enter() {
        return resultMap.success().code(200).message("欢迎进入游客页面" );
    }
}
