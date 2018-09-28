package com.example.ms3;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器。
 *
 * @author freaxjj
 * @date 2018/09/28
 */
@RestController
@RefreshScope//利用总线消息刷新配置
@Api(value = "UserController",description = "用户相关api")//swagger2生成api文档
public class UserController {

    @Value("${nickName}")
    private String nickName;//从配置中心获取配置（覆盖本地配置）

    @Autowired
    private AdminFeignClient adminFeignClient;

    //本地服务
    @ApiOperation(value = "查找用户",notes = "查找用户",httpMethod = "GET",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)//swagger2生成api文档
    @RequestMapping("/getUser")
    public String getUser(@RequestParam(value = "name",defaultValue = "freaxjj") String name) {
        return "name："+ name +";User：" + nickName;
    }

    //调用cloudclient微服务
    @RequestMapping("/getAdmin")
    public String getAdmin() {
        return "User：" + adminFeignClient.getServices();
    }

}
