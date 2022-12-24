package com.example.cn12306s.controller;

import com.example.cn12306s.entity.UserEntity;
import com.example.cn12306s.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class HelloController {

    @Autowired
    private UserService userService;

    @RequestMapping("/hello")
    public String hello(){
        return "hello world! @ "+new Date();
    }

    @GetMapping("/getuser")
    public UserEntity getUser(String username){
        return userService.queryUserByName(username);
    }
}