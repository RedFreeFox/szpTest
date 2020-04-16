package com.szptest.demo.service;

import com.szptest.demo.entity.User;

import java.util.List;

public interface UserService {

    User Sel(int id);
    List<User> getUserList();
}
