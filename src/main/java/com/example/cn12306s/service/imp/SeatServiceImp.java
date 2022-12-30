package com.example.cn12306s.service.imp;

import com.example.cn12306s.dao.ExeTrainStationDAO;
import com.example.cn12306s.dao.OrderDAO;
import com.example.cn12306s.dao.SeatDAO;
import com.example.cn12306s.dto.QueryExeTrain;
import com.example.cn12306s.dto.QueryInfo;
import com.example.cn12306s.dto.SeatOccupancyInfo;
import com.example.cn12306s.dto.SeatQueryInfo;
import com.example.cn12306s.entity.ExeTrainEntity;
import com.example.cn12306s.entity.ExeTrainStationEntity;
import com.example.cn12306s.entity.OrderEntity;
import com.example.cn12306s.entity.SeatEntity;
import com.example.cn12306s.service.ExeTrainService;
import com.example.cn12306s.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private OrderDAO orderDAO;

    @Override
    public List<QueryExeTrain> querySeat(QueryInfo q) throws Exception {
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
            sq.setLeaveStationNo(t.getLeaveStationNo());
            sq.setArriveStationNo(t.getArriveStationNo());
//            System.out.println(new ObjectMapper().writeValueAsString(t));
            List<QueryExeTrain.SeatNum> seatNums = seatDAO.querySeatNum(sq);

            for(QueryExeTrain.SeatNum st : seatNums){
                switch (st.getSeatType()){
                    case 1:
                        t.setSeatANum(st.getSeatNum());
                        t.setSeatAPrice(priceService(t.getExeTrainId(),t.getLeaveStationNo(),t.getArriveStationNo(),st.getSeatType()));
                        break;
                    case 2:
                        t.setSeatBNum(st.getSeatNum());
                        t.setSeatBPrice(priceService(t.getExeTrainId(),t.getLeaveStationNo(),t.getArriveStationNo(),st.getSeatType()));
                        break;
                    case 3:
                        t.setSeatCNum(st.getSeatNum());
                        t.setSeatCPrice(priceService(t.getExeTrainId(),t.getLeaveStationNo(),t.getArriveStationNo(),st.getSeatType()));
                        break;
                    case 4:
                        t.setSeatDNum(st.getSeatNum());
                        t.setSeatDPrice(priceService(t.getExeTrainId(),t.getLeaveStationNo(),t.getArriveStationNo(),st.getSeatType()));
                        break;
                    case 5:
                        t.setSeatENum(st.getSeatNum());
                        t.setSeatEPrice(priceService(t.getExeTrainId(),t.getLeaveStationNo(),t.getArriveStationNo(),st.getSeatType()));
                        break;
                    case 6:
                        t.setSeatFNum(st.getSeatNum());
                        t.setSeatFPrice(priceService(t.getExeTrainId(),t.getLeaveStationNo(),t.getArriveStationNo(),st.getSeatType()));
                        break;
                    case 7:
                        t.setSeatGNum(st.getSeatNum());
                        t.setSeatGPrice(priceService(t.getExeTrainId(),t.getLeaveStationNo(),t.getArriveStationNo(),st.getSeatType()));
                        break;
                }
            }
        }
        return  queryExeTrains;
    }

    private int priceService(int exeTrainId,int leaveStationNo,int arriveStationNo,int seatType) throws Exception {
        int price = (arriveStationNo-leaveStationNo)*100;
        switch (seatType){
            case 1:
                return price;
            case 2:
                return (int)(price*0.8);
            case 3:
                return (int)(price*0.6);
            case 4:
                return (int)(price*0.4);
            case 5:
                return (int)(price*0.6);
            case 6:
                return (int)(price*0.2);
            case 7:
                return (int)(price*0.2);
            default:
                throw new Exception("座位类型错误");
        }
    }

    @Override
    public OrderEntity orderSeat(SeatOccupancyInfo q, long uid) throws Exception {
        long seatId =  seatDAO.seatOccupancy(q);
        if(seatId==0){
            throw new Exception("无票");
        }else {

            SeatEntity seat = seatDAO.getSeatById(seatId);

            ExeTrainStationEntity leaveStationInfo = exeTrainStationDAO.getExeTrainStationById(q.getExeTrainId(), q.getLeaveStationNo());
            ExeTrainStationEntity arriveStationInfo = exeTrainStationDAO.getExeTrainStationById(q.getExeTrainId(), q.getArriveStationNo());

            q.setId(seatId);
            OrderEntity order = new OrderEntity();
            order.setUid(uid)
                    .setTrainName(leaveStationInfo.getTrainName())
                    .setExeTrainId(q.getExeTrainId())
                    .setArriveTs(arriveStationInfo.getArriveTs())
                    .setLeaveTs(leaveStationInfo.getLeaveTs())
                    .setCarNo(seat.getCarNo())
                    .setSeatType(q.getSeatType())
                    .setSeatNo(seat.getSeatNo())
                    .setSeatId(seatId)
                    .setLeaveStationNo(leaveStationInfo.getStationNo())
                    .setArriveStationNo(arriveStationInfo.getStationNo())
                    .setLeaveStationName(leaveStationInfo.getStationName())
                    .setArriveStationName(arriveStationInfo.getStationName())
                    .setPrice(priceService(q.getExeTrainId(),q.getLeaveStationNo(),q.getArriveStationNo(),q.getSeatType()));

            orderDAO.createOrder(order);
            return order;
        }
    }
}
