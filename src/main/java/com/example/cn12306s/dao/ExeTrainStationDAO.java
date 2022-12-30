package com.example.cn12306s.dao;

import com.example.cn12306s.dto.QueryExeTrain;
import com.example.cn12306s.dto.QueryInfo;
import com.example.cn12306s.entity.ExeTrainStationEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ExeTrainStationDAO {

    @Insert("insert into t_exe_train_station (exe_train_id,train_name,station_no,station_name,city_name,sale_ts,arrive_ts,leave_ts) " +
            "values (#{exeTrainId},#{trainName},#{stationNo},#{stationName},#{cityName},#{saleTs},#{arriveTs},#{leaveTs})")
    public void addExeTrainStation(ExeTrainStationEntity e);

    @Delete("delete from t_exe_train_station where exe_train_id=#{id}")
    public void deleteExeTrainStationByExeTrainId(int id);

    @Select("<script>" +
            "select a.exe_train_id,a.train_name " +
            "   ,a.station_name as leave_station " +
            "   ,b.station_name as arrive_station " +
            "   ,a.leave_ts,b.arrive_ts " +
            "   ,a.station_no as leave_station_no " +
            "   ,b.station_no as arrive_station_no " +
            "from ( " +
            "   select exe_train_id,station_no,train_name " +
            "       ,station_name,leave_ts " +
            "   from t_exe_train_station " +
            "   where sale_ts &lt;= #{nowTs} " +
            "       and leave_ts &gt;= #{startTs} " +
            "       and leave_ts &lt; #{endTs} " +
            "       <if test=\"startStation!=null\"> " +
            "       and station_name = #{startStation} " +
            "       </if> " +
            "       <if test=\"startCity!=null\"> " +
            "       and city_name = #{startCity} " +
            "       </if> " +
            ") a join ( " +
            "   select exe_train_id,station_no " +
            "       ,station_name,arrive_ts " +
            "   from t_exe_train_station " +
            "   where sale_ts &lt;= #{nowTs} " +
            "       and arrive_ts &gt;= #{startTs} " +
            "       <if test=\"endStation!=null\"> " +
            "       and station_name = #{endStation} " +
            "       </if> " +
            "       <if test=\"endCity!=null\"> " +
            "       and city_name = #{endCity} " +
            "       </if> " +
            ") b on (a.exe_train_id=b.exe_train_id and a.station_no &lt; b.station_no) " +
            "</script> ")
    public List<QueryExeTrain> queryExeTrain(QueryInfo q);

    @Select(" select id,exe_train_id,train_name,station_no,station_name,city_name,sale_ts,arrive_ts,leave_ts" +
            " from t_exe_train_station" +
            " where exe_train_id=#{exeTrainId} and station_no=#{stationNo}")
    public ExeTrainStationEntity getExeTrainStationById(@Param("exeTrainId") int exeTrainId,@Param("stationNo") int stationNo);
}
