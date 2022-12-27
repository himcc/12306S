package com.example.cn12306s.entity;

import lombok.Data;

@Data
public class TrainEntity {
    private int id;
    private String trainName;
    private String stationJson;
    private String carJson;
}
