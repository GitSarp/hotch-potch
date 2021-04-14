package com.example.shirodemo.service.impl;

import com.example.shirodemo.domain.Account;
import com.example.shirodemo.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/9/21 3:27 PM
 */
@Service
public class AccountServiceImpl implements AccountService {

/*    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account findByUsername(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",username);
        return accountMapper.selectOne(wrapper);
    }*/


    //模拟数据库
    private static ConcurrentHashMap<String,Account> accounts = new ConcurrentHashMap<>();

    static {
        accounts.put("freaxjj", new Account(1, "freajj", "1234", "vip", "admin"));
        accounts.put("sarp", new Account(1, "sarp", "1234", "edit", "admin"));
        accounts.put("ylliu", new Account(1, "ylliu", "1234", "read", "user"));
    }

    @Override
    public Account findByUsername(String username) {
        return accounts.get(username);
    }
}
