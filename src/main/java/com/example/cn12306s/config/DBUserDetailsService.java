package com.example.cn12306s.config;

import com.example.cn12306s.entity.UserEntity;
import com.example.cn12306s.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
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
        DBUser result = new DBUser(user.getId(),user.getUsername(),user.getPasswd(),authorities);
        return result;
    }
    public static class DBUser extends User implements UserDetails{

        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public DBUser(long id, String username, String password, Collection<? extends GrantedAuthority> authorities){
            super(username,password,authorities);
            this.id=id;
        }
    }
}
