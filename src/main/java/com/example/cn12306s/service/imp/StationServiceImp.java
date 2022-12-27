package com.example.cn12306s.service.imp;

import com.example.cn12306s.dao.StationDAO;
import com.example.cn12306s.entity.StationEntity;
import com.example.cn12306s.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImp implements StationService {

    @Autowired
    private StationDAO stationDAO;

    @Override
    public List<StationEntity> getAllStation() {
        return stationDAO.getAllStation();
    }

    @Override
    public void addStation(StationEntity station) {
        stationDAO.addStation(station);
    }
}
