package com.example.cn12306s.dao;

import com.example.cn12306s.entity.ExeTrainStationEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExeTrainStationDAO {

    @Insert("insert into t_exe_train_station (exe_train_id,train_name,station_no,station_name,city_name,sale_ts,arrive_ts,leave_ts) " +
            "values (#{exeTrainId},#{trainName},#{stationNo},#{stationName},#{cityName},#{saleTs},#{arriveTs},#{leaveTs})")
    public void addExeTrainStation(ExeTrainStationEntity e);

    @Delete("delete from t_exe_train_station where exe_train_id=#{id}")
    public void deleteExeTrainStationByExeTrainId(int id);
}
