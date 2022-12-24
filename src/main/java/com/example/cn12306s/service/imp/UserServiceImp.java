package com.example.cn12306s.service.imp;

import com.example.cn12306s.dao.UserDAO;
import com.example.cn12306s.entity.UserEntity;
import com.example.cn12306s.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserDAO userDao;

    @Override
    public UserEntity queryUserByName(String username) {
        return userDao.findByName(username);
    }
}
