package com.example.cn12306s.config;

import com.example.cn12306s.entity.UserEntity;
import com.example.cn12306s.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DBUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.queryUserByName(username);
        if (null==user){
            throw new UsernameNotFoundException("用户名错误");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getSysRole()==0?"ROLE_admin":"ROLE_user"));
        org.springframework.security.core.userdetails.User result =
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),user.getPasswd(),authorities
                );
        return result;
    }
}
