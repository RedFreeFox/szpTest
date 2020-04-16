package com.szpcons.szpcons.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.szpcons.szpcons.util.FileToZipUtil;
import com.szptest.demo.entity.User;
import com.szptest.demo.service.UserService;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/test/")
public class UserController {
    @Reference(version = "1.0.0")
    UserService userService;
    @RequestMapping(value = "getUser/{id}")
    //@GetMapping(value = "getUser/{id}")
    public String GetUser(@PathVariable int id, Model model){
        User sel = userService.Sel(id);
        model.addAttribute("user", sel.getUserName());
        return "text";
    }

    @RequestMapping(value = "getUser/list")
    public String GetUserList( Model model){
        List<User> userList = userService.getUserList();
        model.addAttribute("users", userList);
        return "text";
    }
    @RequestMapping(value = "getUser/load")
    public void GetUserLoad(Model model, HttpServletResponse response, HttpServletRequest request){
        List<User> userList = userService.getUserList();
        //model.addAttribute("users", userList);
        //response.setContentType("application/x-msdownload;charset=utf-8");
        // new String(zipFileName.getBytes("utf-8"), "ISO_8859_1")
        //response.addHeader("Content-Disposition", "attachment; filename=\"" + "aaa.txt" + "\"");
        OutputStream stream=null;
        File file=new File("aaa.txt");
        try {
            stream=new BufferedOutputStream(new FileOutputStream(file));
            //stream=response.getOutputStream();
            for (User user:userList) {
                stream.write(user.getUserName().getBytes());
            }
            stream.flush();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileToZipUtil.fileToZip(response, "aaa.zip", file, "aaa.txt", "123");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

}
