package com.zj.controller;

import com.zj.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ziJun
 */
@Controller
@RequestMapping("view")
public class UserController {

    @RequestMapping(value = "/user", method = {RequestMethod.GET})
    public ModelAndView getUser() {
        User user = new User();
        user.setId(1000);
        user.setName("tommy");
        user.setEmail("986654@qq.com");
        ModelAndView view = new ModelAndView("userInfo");
        view.addObject("user", user);
        return view;
    }
}