package com.example.ms3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器。
 *
 * @author Ewing
 * @date 2017/4/25
 */
@RestController
@RefreshScope//?
public class UserController {

    @Value("${nickName}")
    private String nickName;

    @Autowired
    private AdminFeignClient adminFeignClient;//调用cloudclient

    @RequestMapping("/getUser")
    public String getUser() {
        return "User：" + nickName;
    }

    @RequestMapping("/getAdmin")
    public String getAdmin() {
        return "User：" + adminFeignClient.getServices();
    }

}
