package com.example.cn12306s.utils;

import com.example.cn12306s.dto.SeatQueryInfo;

public class SeatProvider {


    public String querySeatNumSQL(SeatQueryInfo q){

        StringBuffer sb = new StringBuffer();

        sb.append(" select seat_type,count(1) as seat_num from t_seat where exe_train_id="+q.getExeTrainId());
        for(int i=q.getLeavStationNo();i<q.getArriveStationNo();i++){
            sb.append(" and seg"+i+" = 0");
        }

        sb.append(" group by seat_type");

        return sb.toString();

    }

    public static void main(String[] args) {
        SeatQueryInfo q=new SeatQueryInfo();
        q.setExeTrainId(100);
        q.setLeavStationNo(5);
        q.setArriveStationNo(8);
        System.out.println(new SeatProvider().querySeatNumSQL(q));
    }
}
