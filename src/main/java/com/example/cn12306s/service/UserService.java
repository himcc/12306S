package com.example.cn12306s.service;

import com.example.cn12306s.entity.UserEntity;

public interface UserService {

    public UserEntity queryUserByName(String username);

    public void addUser(UserEntity user);

}
