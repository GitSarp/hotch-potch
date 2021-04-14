package com.example.shirodemo.controller;

import com.example.shirodemo.exception.JwtException;
import com.example.shirodemo.vo.JwtResultMap;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/12/21 2:04 PM
 */
@RestController
@RequestMapping("/user")
public class UserController extends JwtException {
    @Autowired
    private JwtResultMap resultMap;

    @RequiresRoles(logical = Logical.OR,value = {"user","admin"})
    @GetMapping("/enter")
    public JwtResultMap enter(){
        return resultMap.success().code(200).message("欢迎进入用户页面");
    }

    @RequiresPermissions("vip")
    @RequiresRoles(logical = Logical.OR,value = {"user","admin"})
    @GetMapping("/getMessage")
    public JwtResultMap getMessage(){
        return resultMap.success().code(200).message("成功获得vip信息");
    }

}
