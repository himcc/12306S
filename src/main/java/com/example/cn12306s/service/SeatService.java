package com.example.cn12306s.service;

import com.example.cn12306s.dto.QueryExeTrain;
import com.example.cn12306s.dto.QueryInfo;
import com.example.cn12306s.dto.SeatOccupancyInfo;
import com.example.cn12306s.entity.OrderEntity;

import java.util.List;

public interface SeatService {

    public List<QueryExeTrain> querySeat(QueryInfo q) throws Exception;

    public OrderEntity orderSeat(SeatOccupancyInfo q, long uid) throws Exception;
}
