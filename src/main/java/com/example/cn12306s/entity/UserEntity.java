package com.example.cn12306s.entity;

import lombok.Data;

@Data
public class UserEntity {
    private long id;
    private String username;
    private String passwd;
    private int sysRole;
}
