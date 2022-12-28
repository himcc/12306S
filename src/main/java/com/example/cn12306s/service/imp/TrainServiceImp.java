package com.example.cn12306s.service.imp;

import com.example.cn12306s.dao.TrainDAO;
import com.example.cn12306s.dto.TrainStation;
import com.example.cn12306s.entity.TrainEntity;
import com.example.cn12306s.service.TrainService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public void addTrain(TrainEntity train) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<TrainStation> stationList= mapper.readValue(train.getStationJson(),new TypeReference<List<TrainStation>>(){});
        if(stationList.size()>20){
            throw new Exception("站点数量最多20个");
        }
        trainDAO.addTrain(train);
    }

    @Override
    public int deleteTrain(int id) {
        return trainDAO.deleteTrain(id);
    }


}
