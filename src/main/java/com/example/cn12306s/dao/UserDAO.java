package com.example.cn12306s.dao;

import com.example.cn12306s.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDAO {
    @Select("select id,username,passwd,sys_role from t_user where username=#{username}")
    public UserEntity findByName(String username);

    @Insert("insert into t_user (username,passwd) values (#{username},#{passwd})")
    public void addUser(UserEntity user);
}