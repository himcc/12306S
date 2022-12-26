package com.example.cn12306s.service.imp;

import com.example.cn12306s.dao.UserDAO;
import com.example.cn12306s.entity.UserEntity;
import com.example.cn12306s.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserDAO userDao;

    @Override
    public UserEntity queryUserByName(String username) {
        return userDao.findByName(username);
    }

    @Override
    public void addUser(UserEntity user) {
        userDao.addUser(user);
    }

    @Override
    public int changePasswd(UserEntity user) {
        return userDao.updatePasswd(user);
    }

    @Override
    public List<UserEntity> getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    public int changeSysRole(UserEntity user) {
        return userDao.updateSysRole(user);
    }
}
