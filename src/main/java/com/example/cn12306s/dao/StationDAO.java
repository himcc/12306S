package com.example.cn12306s.dao;

import com.example.cn12306s.entity.StationEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StationDAO {

    @Select("select * from t_station")
    public List<StationEntity> getAllStation();

    @Insert("insert into t_station (station_name,city_name) values (#{stationName},#{cityName})")
    public void addStation(StationEntity station);
}
