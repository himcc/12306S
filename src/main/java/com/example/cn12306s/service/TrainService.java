package com.example.cn12306s.service;

import com.example.cn12306s.entity.TrainEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface TrainService {

    List<TrainEntity> getAllTrain();

    public void addTrain(TrainEntity train) throws Exception;

    public int deleteTrain(int id);
}
