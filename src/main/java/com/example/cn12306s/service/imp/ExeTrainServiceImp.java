package com.example.cn12306s.service.imp;

import com.example.cn12306s.dao.ExeTrainDAO;
import com.example.cn12306s.dao.ExeTrainStationDAO;
import com.example.cn12306s.dao.StationDAO;
import com.example.cn12306s.dao.TrainDAO;
import com.example.cn12306s.dto.TrainStation;
import com.example.cn12306s.entity.ExeTrainEntity;
import com.example.cn12306s.entity.ExeTrainStationEntity;
import com.example.cn12306s.entity.StationEntity;
import com.example.cn12306s.entity.TrainEntity;
import com.example.cn12306s.service.ExeTrainService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExeTrainServiceImp implements ExeTrainService {

    @Autowired
    private ExeTrainDAO exeTrainDAO;

    @Autowired
    private ExeTrainStationDAO exeTrainStationDAO;

    @Autowired
    private TrainDAO trainDAO;

    @Autowired
    private StationDAO stationDAO;

    private Map<String,String> getStationCityMap(){
        HashMap<String,String> ret = new HashMap<String,String>();
        List<StationEntity> allStation = stationDAO.getAllStation();
        for(StationEntity s : allStation){
            ret.put(s.getStationName(),s.getCityName());
        }
        return ret;
    }

    @Override
    public void addExeTrain(ExeTrainEntity e) throws Exception {

        TrainEntity train = trainDAO.getTrainByName(e.getTrainName());

        if(train==null){
            throw new Exception(e.getTrainName()+"不存在");
        }

        e.setStationJson(train.getStationJson());
        e.setCarJson(train.getCarJson());

        exeTrainDAO.addExeTrain(e);

        ObjectMapper mapper = new ObjectMapper();

        List<TrainStation> stationList= mapper.readValue(e.getStationJson(),new TypeReference<List<TrainStation>>(){});

        Map<String,String> stationCityMap = getStationCityMap();

        DateTime dt = new DateTime(e.getExeDate());

        for(TrainStation s : stationList){
            ExeTrainStationEntity ets = new ExeTrainStationEntity();
            ets.setExeTrainId(e.getId());
            ets.setTrainName(e.getTrainName());
            ets.setStationNo(s.getId());
            ets.setStationName(s.getStationName());
            ets.setCityName(stationCityMap.get(s.getStationName()));
            ets.setSaleTs(e.getSaleTs());
            ets.setArriveTs(dt.plusMinutes(s.getArriveMin()).getMillis());
            ets.setLeaveTs(dt.plusMinutes(s.getLeaveMin()).getMillis());
            exeTrainStationDAO.addExeTrainStation(ets);
        }

        // TODO insert seat
    }

    @Override
    public List<ExeTrainEntity> getAllExeTrain() {
        return exeTrainDAO.getAllExeTrain();
    }

    @Override
    public void deleteExeTrain(int id) {
        exeTrainDAO.deleteExeTrain(id);
        exeTrainStationDAO.deleteExeTrainStationByExeTrainId(id);
        // TODO  delete seat
    }

}
