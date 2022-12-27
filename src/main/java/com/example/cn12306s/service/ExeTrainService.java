package com.example.cn12306s.service;

import com.example.cn12306s.entity.ExeTrainEntity;
import com.example.cn12306s.entity.ExeTrainStationEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ExeTrainService {

    public void addExeTrain(ExeTrainEntity e) throws Exception;

    public List<ExeTrainEntity> getAllExeTrain();

    public void deleteExeTrain(int id);

}
