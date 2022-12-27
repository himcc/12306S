package com.example.cn12306s.service.imp;

import com.example.cn12306s.dao.TrainDAO;
import com.example.cn12306s.entity.TrainEntity;
import com.example.cn12306s.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainServiceImp implements TrainService {

    @Autowired
    private TrainDAO trainDAO;

    @Override
    public List<TrainEntity> getAllTrain() {
        return trainDAO.getAllTrain();
    }

    @Override
    public void addTrain(TrainEntity train) {
        trainDAO.addTrain(train);
    }

    @Override
    public int deleteTrain(int id) {
        return trainDAO.deleteTrain(id);
    }


}
