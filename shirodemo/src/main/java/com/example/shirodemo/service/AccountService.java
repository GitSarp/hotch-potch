package com.example.shirodemo.service;

import com.example.shirodemo.domain.Account;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/9/21 3:26 PM
 */
public interface AccountService {
    Account findByUsername(String username);
}
