package com.example.cn12306s.service;

import com.example.cn12306s.entity.UserEntity;

import java.util.List;

public interface UserService {

    public UserEntity queryUserByName(String username);

    public void addUser(UserEntity user);

    public int changePasswd(UserEntity user);

    public List<UserEntity> getAllUser();

    public int changeSysRole(UserEntity user);

}
