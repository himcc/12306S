package com.example.cn12306s.service;

import com.example.cn12306s.entity.TrainEntity;

import java.util.List;

public interface TrainService {

    List<TrainEntity> getAllTrain();

    public void addTrain(TrainEntity train);

    public int deleteTrain(int id);
}
