package com.example.cn12306s.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class SeatOccupancyInfo {
    private long id;
    private int exeTrainId;
    private int leaveStationNo;
    private int arriveStationNo;
    private int seatType;
}
