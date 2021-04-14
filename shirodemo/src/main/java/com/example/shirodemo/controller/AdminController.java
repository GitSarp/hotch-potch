package com.example.shirodemo.controller;

import com.example.shirodemo.vo.JwtResultMap;
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
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private JwtResultMap resultMap;

    @RequiresRoles("admin")
    @GetMapping("/enter")
    public JwtResultMap enter(){
        return resultMap.success().code(200).message("Admin Enter");
    }
}
