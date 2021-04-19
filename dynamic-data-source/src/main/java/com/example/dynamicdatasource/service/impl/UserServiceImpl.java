package com.example.dynamicdatasource.service.impl;

import com.example.dynamicdatasource.entity.User;
import com.example.dynamicdatasource.mapper.UserMapper;
import com.example.dynamicdatasource.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author freaxjj
 * @since 2021-04-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
