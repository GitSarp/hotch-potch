package com.example.shirodemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/9/21 3:25 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Integer id;
    private String username;
    private String password;
    //权限
    private String perms;
    //角色
    private String role;
}
