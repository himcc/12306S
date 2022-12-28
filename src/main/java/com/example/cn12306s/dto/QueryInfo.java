package com.example.cn12306s.dto;

import lombok.Data;

@Data
public class QueryInfo {
    private Long nowTs;
    private Long startTs;
    private Long endTs;
    private String startStation;
    private String startCity;
    private String endStation;
    private String endCity;
}
