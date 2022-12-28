package com.example.cn12306s.entity;

import lombok.Data;

@Data
public class SeatEntity {
    private long id;
    private int exeTrainId;
    private int carNo;
    private int seatType;
    private int seatNo;
}
