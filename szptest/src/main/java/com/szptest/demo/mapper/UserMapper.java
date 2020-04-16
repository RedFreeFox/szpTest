package com.szptest.demo.mapper;

import com.szptest.demo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    User Sel(int id);
    List<User> getUserList();
}
