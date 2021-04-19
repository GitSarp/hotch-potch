package com.example.springbootdemo.controller;

import com.example.dynamicdatasource.DynamicDataSource;
import com.example.dynamicdatasource.annotation.Tenant;
import com.example.dynamicdatasource.config.DataSourceConfig;
import com.example.dynamicdatasource.config.DataSourceProperty;

import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/16/21 10:40 AM
 */
@RestController
@Slf4j
public class TestController {
    @Autowired
    private IUserService userService;

    //不同租户查不同的数据源
    @GetMapping("/users")
    @Tenant
    public List<User> getTenantInfo(String tenantId){
        log.info("查询租户[{}]的所有用户", tenantId);
        List<User> users = userService.list();
        return users;
    }

    @Autowired
    private DynamicDataSource dynamicDataSource;

    //动态添加 租户及数据源
    @PostMapping("/datasource/add")
    public String addTenant(String tenantId, DataSourceProperty property){
        DataSource dataSource = DataSourceConfig.createDataSource(property);
        dynamicDataSource.addTargetDataSource(tenantId, dataSource);
        return "success!";
    }
}
