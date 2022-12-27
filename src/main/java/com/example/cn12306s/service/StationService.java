package com.example.cn12306s.service;

import com.example.cn12306s.entity.StationEntity;

import java.util.List;

public interface StationService {

    public List<StationEntity> getAllStation();

    public void addStation(StationEntity station);
}
