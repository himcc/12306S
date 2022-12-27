package com.example.cn12306s.entity;

import lombok.Data;

@Data
public class ExeTrainStationEntity {
    private int id;
    private int exeTrainId;
    private String trainName;
    private int stationNo;
    private String stationName;
    private String cityName;
    private long saleTs;
    private long arriveTs;
    private long leaveTs;
}
