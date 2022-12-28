package com.example.cn12306s;

import org.apache.ibatis.annotations.Select;

public class TMPMain {

    public static void main(String[] args) {
        String x = "<script>" +
                "select a.exe_train_id,a.train_name" +
                "   ,a.station_name as leave_station" +
                "   ,b.station_name as arrive_station" +
                "   ,a.leave_ts,b.arrive_ts" +
                "   ,a.station_no as leav_station_no" +
                "   ,b.station_no as arrive_station_no" +
                "from (" +
                "   select exe_train_id,station_no,train_name" +
                "       ,station_name,leave_ts" +
                "   from t_exe_train_station" +
                "   where sale_ts<=#{nowTs}" +
                "       and leave_ts>=#{startTs}" +
                "       and leave_ts<#{endTs}" +
                "       <if test=\"startStation!=null\">" +
                "       and station_name = #{startStation}" +
                "       </if>" +
                "       <if test=\"startCity!=null\">" +
                "       and city_name = #{startCity}" +
                "       </if>" +
                ") a join (" +
                "   select exe_train_id,station_no" +
                "       ,station_name,arrive_ts" +
                "   from t_exe_train_station" +
                "   where sale_ts<=#{nowTs}" +
                "       and arrive_ts>=#{startTs}" +
                "       <if test=\"endStation!=null\">" +
                "       and station_name = #{endStation}" +
                "       </if>" +
                "       <if test=\"endCity!=null\">" +
                "       and city_name = #{endCity}" +
                "       </if>" +
                ") b on (a.exe_train_id=b.exe_train_id and a.station_no<b.station_no)" +
                "</script>";
        System.out.println(x);
    }
}
