package com.example.cn12306s.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class OrderEntity {
    private Long id;
    private Long uid;
    private String trainName;
    private Integer exeTrainId;
    private Long arriveTs;
    private Long leaveTs;
    private int carNo;
    private int seatType;
    private int seatNo;
    private Long seatId;
    private Integer leaveStationNo;
    private Integer arriveStationNo;
    private String leaveStationName;
    private String arriveStationName;
    private int price;
    private int orderStatus;  //0 下单，1 已支付，2 退票，3 支付超时自动取消
    private Timestamp createTime;
}
