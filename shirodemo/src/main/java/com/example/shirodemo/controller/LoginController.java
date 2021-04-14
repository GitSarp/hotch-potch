package com.example.shirodemo.controller;

import com.example.shirodemo.domain.Account;
import com.example.shirodemo.service.AccountService;
import com.example.shirodemo.util.JwtUtil;
import com.example.shirodemo.vo.JwtResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/12/21 2:12 PM
 */
@Controller
public class LoginController {
    @Autowired
    private JwtResultMap resultMap;
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    @ResponseBody
    public JwtResultMap login(String username,String password){
        Account account = accountService.findByUsername(username);
        if (null==account || !account.getPassword().equals(password)){
            return resultMap.fail().code(401).message("账户或密码错误");
        }
        return resultMap.success().code(200).message(JwtUtil.createToken(username));
    }

    @ResponseBody
    @GetMapping("/unauthorized/{message}")
    public JwtResultMap unauthorized(@PathVariable String message){
        return resultMap.success().code(401).message(message);
    }
}
