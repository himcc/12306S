package com.example.cn12306s.dao;

import com.example.cn12306s.entity.ExeTrainEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ExeTrainDAO {

    @Insert("insert into t_exe_train (train_name,exe_date,sale_ts,station_json,car_json) " +
            "values (#{trainName},#{exeDate},#{saleTs},#{stationJson},#{carJson})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int addExeTrain(ExeTrainEntity e);

    @Select("select * from t_exe_train")
    public List<ExeTrainEntity> getAllExeTrain();

    @Delete("delete from t_exe_train where id=#{id}")
    public void deleteExeTrain(int id);
}
