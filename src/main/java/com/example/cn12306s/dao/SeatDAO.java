package com.example.cn12306s.dao;

import com.example.cn12306s.dto.QueryExeTrain;
import com.example.cn12306s.dto.SeatOccupancyInfo;
import com.example.cn12306s.dto.SeatQueryInfo;
import com.example.cn12306s.entity.OrderEntity;
import com.example.cn12306s.entity.SeatEntity;
import com.example.cn12306s.utils.SeatProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SeatDAO {

    @Insert("insert into t_seat (exe_train_id,car_no,seat_type,seat_no) values (#{exeTrainId},#{carNo},#{seatType},#{seatNo})")
    public void addSeat(SeatEntity e);

    @Delete("delete from t_seat where exe_train_id=#{id}")
    public void deleteSeatByExeTrainId(int id);

    @SelectProvider(type = SeatProvider.class, method = "querySeatNumSQL")
    public List<QueryExeTrain.SeatNum> querySeatNum(SeatQueryInfo q);

    @SelectProvider(type = SeatProvider.class, method = "seatOccupancySQL")
    public long seatOccupancy(SeatOccupancyInfo q);

    @Select("select id,exe_train_id,car_no,seat_type,seat_no from t_seat where id=#{id}")
    public SeatEntity getSeatById(long id);

    @UpdateProvider(type = SeatProvider.class, method = "refundSeatSQL")
    public int refundSeat(OrderEntity e);

}
