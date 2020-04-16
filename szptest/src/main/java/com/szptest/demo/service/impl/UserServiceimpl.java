package com.szptest.demo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.szptest.demo.entity.User;
import com.szptest.demo.mapper.UserMapper;
import com.szptest.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service(version = "1.0.0", timeout = 10000, interfaceClass = UserService.class)
@Component
public class UserServiceimpl implements UserService {

    @Autowired
    UserMapper userMapper;
    public User Sel(int id){
        return userMapper.Sel(id);
    }

    @Override
    public List<User> getUserList() {
        PageHelper.startPage(1, 1);
        List<User> userList = userMapper.getUserList();
        return userList;
    }

}
