package com.example.cn12306s.controller;

import com.example.cn12306s.config.DBUserDetailsService;
import com.example.cn12306s.dto.RetData;
import com.example.cn12306s.entity.UserEntity;
import com.example.cn12306s.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private UserService userService;
    @GetMapping("/admin/getalluser")
    public RetData getAllUser(){
        List<UserEntity> allUser = userService.getAllUser();
        RetData ret = new RetData();
        ret.setCode(10000);
        ret.setMsg("ok");
        ret.setData(allUser);
        return ret;
    }

    @GetMapping("/admin/roleswitch")
    public RetData roleSwitch(@RequestParam Integer uid, @RequestParam Integer newRole){
        RetData ret = new RetData();

        try {
            UserEntity user = new UserEntity();
            user.setId(uid);
            user.setSysRole(newRole);

            userService.changeSysRole(user);
            ret.setCode(10000);
            ret.setMsg("成功！");
            return ret;
        } catch (Exception e) {
            ret.setCode(10002);
            ret.setMsg(e.getClass().getName()+" "+e.getLocalizedMessage());
            return ret;
        }
    }
}
