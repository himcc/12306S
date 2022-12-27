package com.example.cn12306s.dao;

import com.example.cn12306s.entity.TrainEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TrainDAO {

    @Select("select * from t_train")
    public List<TrainEntity> getAllTrain();

    @Insert("insert into t_train (train_name,station_json,car_json) values (#{trainName},#{stationJson},#{carJson})")
    public void addTrain(TrainEntity train);

    @Delete("delete from t_train where id=#{id}")
    public int deleteTrain(int id);
}
