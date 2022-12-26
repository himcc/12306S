package com.example.cn12306s.dao;

import com.example.cn12306s.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserDAO {
    @Select("select id,username,passwd,sys_role from t_user where username=#{username}")
    public UserEntity findByName(String username);

    @Insert("insert into t_user (username,passwd) values (#{username},#{passwd})")
    public void addUser(UserEntity user);

    @Update("update t_user set passwd=#{passwd} where id=#{id}")
    public int updatePasswd(UserEntity user);

    @Select("select id,username,sys_role from t_user")
    public List<UserEntity> getAllUser();

    @Update("update t_user set sys_role=#{sysRole} where id=#{id}")
    public int updateSysRole(UserEntity user);
}
