package com.example.cn12306s.service;

import com.example.cn12306s.dto.QueryExeTrain;
import com.example.cn12306s.dto.QueryInfo;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface SeatService {

    public List<QueryExeTrain> querySeat(QueryInfo q) throws JsonProcessingException;
}
