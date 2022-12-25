package com.example.cn12306s.controller;

import com.example.cn12306s.dto.RetData;
import com.example.cn12306s.entity.UserEntity;
import com.example.cn12306s.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/reg")
    public RetData reg(String username,String password){
        RetData ret = new RetData();

        try{
            UserEntity user = new UserEntity();
            user.setUsername(username);
            user.setPasswd(new BCryptPasswordEncoder().encode(password));
            userService.addUser(user);
            ret.setCode(10000);
            ret.setMsg("注册成功！");
            return ret;
        }catch (DuplicateKeyException e){
            ret.setCode(10001);
            ret.setMsg(e.getClass().getName()+" "+e.getLocalizedMessage());
            return ret;
        }catch (Exception e){
            ret.setCode(10002);
            ret.setMsg(e.getClass().getName()+" "+e.getLocalizedMessage());
            return ret;
        }
    }

}