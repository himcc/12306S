package com.example.cn12306s.dto;

import lombok.Data;

@Data
public class QueryExeTrain {

    private int exeTrainId;
    private String trainName;
    private String leaveStation;
    private String arriveStation;
    private long leaveTs;
    private long arriveTs;

    private int leaveStationNo;
    private int arriveStationNo;


    private Integer seatANum;
    private Integer seatBNum;
    private Integer seatCNum;
    private Integer seatDNum;
    private Integer seatENum;
    private Integer seatFNum;
    private Integer seatGNum;

    private Integer seatAPrice;
    private Integer seatBPrice;
    private Integer seatCPrice;
    private Integer seatDPrice;
    private Integer seatEPrice;
    private Integer seatFPrice;
    private Integer seatGPrice;

    private String stationJson;

    @Data
    public static class SeatNum{
        private int exeTrainId;
        private int seatType;
        private int seatNum;
    }
}
