package com.example.cn12306s.dto;


import lombok.Data;

@Data
public class TrainStation {
    private int id;
    private String stationName;
    private int arriveMin;
    private int leaveMin;
}
