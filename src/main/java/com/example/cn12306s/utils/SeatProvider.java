package com.example.cn12306s.utils;

import com.example.cn12306s.dto.SeatOccupancyInfo;
import com.example.cn12306s.dto.SeatQueryInfo;
import com.example.cn12306s.entity.OrderEntity;

public class SeatProvider {


    public String querySeatNumSQL(SeatQueryInfo q){

        StringBuffer sb = new StringBuffer();

        sb.append(" select seat_type,count(1) as seat_num from t_seat where exe_train_id="+q.getExeTrainId());
        for(int i = q.getLeaveStationNo(); i<q.getArriveStationNo(); i++){
            sb.append(" and seg"+i+" = 0");
        }

        sb.append(" group by seat_type");

        return sb.toString();

    }

    public String seatOccupancySQL(SeatOccupancyInfo q){

        StringBuffer sb = new StringBuffer();
        sb.append(" SET @update_id := 0;");
        sb.append("UPDATE t_seat SET ");
        for(int i = q.getLeaveStationNo(); i<q.getArriveStationNo(); i++){
            sb.append(" seg"+i+" = 1,");
        }
        sb.append(" id = (SELECT @update_id := id)");
        sb.append(" WHERE exe_train_id=").append(q.getExeTrainId())
                .append(" and seat_type=").append(q.getSeatType());

        for(int i = q.getLeaveStationNo(); i<q.getArriveStationNo(); i++){
            sb.append(" and seg"+i+" = 0 ");
        }
        sb.append(" limit 1;");
        sb.append("SELECT @update_id;");

//        同一个连接，多次执行的时候，即使后续的update没有更新成功，也能select到之前的LAST_INSERT_ID()
//        StringBuffer sb = new StringBuffer();
//        sb.append("UPDATE t_seat SET ");
//        for(int i = q.getLeaveStationNo(); i<q.getArriveStationNo(); i++){
//            sb.append(" seg"+i+" = 1,");
//        }
//        sb.append(" id = LAST_INSERT_ID(id)");
//        sb.append(" WHERE exe_train_id=").append(q.getExeTrainId())
//                .append(" and seat_type=").append(q.getSeatType());
//
//        for(int i = q.getLeaveStationNo(); i<q.getArriveStationNo(); i++){
//            sb.append(" and seg"+i+" = 0 ");
//        }
//        sb.append(" limit 1;");
//        sb.append(" select LAST_INSERT_ID();");


        return sb.toString();
    }

    public String refundSeatSQL(OrderEntity e){
        StringBuffer sb = new StringBuffer();

        sb.append(" update t_seat set ");
        for(int i = e.getLeaveStationNo(); i<e.getArriveStationNo(); i++){
            if(i!=e.getLeaveStationNo()){
                sb.append(",");
            }
            sb.append(" seg"+i+" = 0 ");
        }
        sb.append(" where id = "+e.getSeatId());

        return sb.toString();
    }

    public static void main(String[] args) {
        OrderEntity q=new OrderEntity();
        q.setLeaveStationNo(5);
        q.setArriveStationNo(8);
        q.setSeatId(1000l);
        System.out.println(new SeatProvider().refundSeatSQL(q));
    }
}
