package com.example.cn12306s.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class ExeTrainEntity {
    private int id;
    private String trainName;
    private Date exeDate;
    private long saleTs;
    private String stationJson;
    private String carJson;
}
