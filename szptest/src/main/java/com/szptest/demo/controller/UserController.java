package com.szptest.demo.controller;

import com.szptest.demo.entity.User;
import com.szptest.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/")
public class UserController {

    @Autowired
    UserService userService;
    //@RequestMapping(value = "getUser/{id}")
    @GetMapping(value = "getUser/{id}")
    public User GetUser(@PathVariable int id){
        return userService.Sel(id);
    }

}
