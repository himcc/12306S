package com.example.cn12306s.dto;

import lombok.Data;

@Data
public class SeatQueryInfo {
    private int exeTrainId;
    private int leaveStationNo;
    private int arriveStationNo;
}
