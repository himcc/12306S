package com.example.cn12306s.dao;

import com.example.cn12306s.entity.OrderEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderDAO {

    @Insert("insert into t_order " +
            "(uid, train_name, exe_train_id, arrive_ts, leave_ts, car_no, seat_type, seat_no, seat_id, leave_station_no, arrive_station_no, leave_station_name, arrive_station_name, price)" +
            "values (#{uid},#{trainName},#{exeTrainId},#{arriveTs},#{leaveTs},#{carNo},#{seatType},#{seatNo},#{seatId},#{leaveStationNo},#{arriveStationNo},#{leaveStationName},#{arriveStationName},#{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int createOrder(OrderEntity e);

    @Update("update t_order set order_status=1 where id=#{id}")
    public int paySuccess(long id);

    @Select("select * from t_order where uid=#{uid} order by create_time desc")
    public List<OrderEntity> getOrderSByUid(long uid);

    @Update("update t_order set order_status=2 where id=#{id}")
    public int refundTicket(long id);

    @Select("select * from t_order where id=#{id}")
    public OrderEntity getOrderById(long id);

    @Delete("delete from t_order where id=#{id}")
    public int deleteOrder(long id);

}
