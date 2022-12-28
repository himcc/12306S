package com.example.cn12306s.service.imp;

import com.example.cn12306s.dao.ExeTrainStationDAO;
import com.example.cn12306s.dao.SeatDAO;
import com.example.cn12306s.dto.QueryExeTrain;
import com.example.cn12306s.dto.QueryInfo;
import com.example.cn12306s.dto.SeatQueryInfo;
import com.example.cn12306s.entity.ExeTrainEntity;
import com.example.cn12306s.service.ExeTrainService;
import com.example.cn12306s.service.SeatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeatServiceImp implements SeatService {

    @Autowired
    private ExeTrainStationDAO exeTrainStationDAO;

    @Autowired
    private SeatDAO seatDAO;

    @Autowired
    private ExeTrainService exeTrainService;

    @Override
    public List<QueryExeTrain> querySeat(QueryInfo q) throws JsonProcessingException {
        List<QueryExeTrain> queryExeTrains = exeTrainStationDAO.queryExeTrain(q);

        List<ExeTrainEntity> allExeTrain = exeTrainService.getAllExeTrain();
        Map<Integer,ExeTrainEntity> map = new HashMap<>();
        for(ExeTrainEntity e : allExeTrain){
            map.put(e.getId(),e);
        }
        // seat num
        for(QueryExeTrain t : queryExeTrains){

            t.setStationJson(map.get(t.getExeTrainId()).getStationJson());

            SeatQueryInfo sq = new SeatQueryInfo();
            sq.setExeTrainId(t.getExeTrainId());
            sq.setLeavStationNo(t.getLeaveStationNo());
            sq.setArriveStationNo(t.getArriveStationNo());
//            System.out.println(new ObjectMapper().writeValueAsString(t));
            List<QueryExeTrain.SeatNum> seatNums = seatDAO.querySeatNum(sq);

            int price = (t.getArriveStationNo()-t.getLeaveStationNo())*100;

            for(QueryExeTrain.SeatNum st : seatNums){
                switch (st.getSeatType()){
                    case 1:
                        t.setSeatANum(st.getSeatNum());
                        t.setSeatAPrice(price);
                        break;
                    case 2:
                        t.setSeatBNum(st.getSeatNum());
                        t.setSeatBPrice((int)(price*0.8));
                        break;
                    case 3:
                        t.setSeatCNum(st.getSeatNum());
                        t.setSeatCPrice((int)(price*0.6));
                        break;
                    case 4:
                        t.setSeatDNum(st.getSeatNum());
                        t.setSeatDPrice((int)(price*0.4));
                        break;
                    case 5:
                        t.setSeatENum(st.getSeatNum());
                        t.setSeatEPrice((int)(price*0.6));
                        break;
                    case 6:
                        t.setSeatFNum(st.getSeatNum());
                        t.setSeatFPrice((int)(price*0.2));
                        break;
                    case 7:
                        t.setSeatGNum(st.getSeatNum());
                        t.setSeatGPrice((int)(price*0.2));
                        break;
                }
            }
        }
        return  queryExeTrains;
    }
}
