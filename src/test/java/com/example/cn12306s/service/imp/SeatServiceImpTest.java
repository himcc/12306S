package com.example.cn12306s.service.imp;

import com.example.cn12306s.dto.SeatOccupancyInfo;
import com.example.cn12306s.entity.OrderEntity;
import com.example.cn12306s.service.SeatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SeatServiceImpTest {

    @Autowired
    private SeatService seatService;

    @Test
    void seatOccupancyTest() throws Exception {
        SeatOccupancyInfo q = new SeatOccupancyInfo();

        q.setExeTrainId(5)
                .setSeatType(1)
                .setLeaveStationNo(2)
                .setArriveStationNo(4);

        OrderEntity ret = seatService.orderSeat(q,1);

        System.out.println("omg "+ret);

//        ret = seatService.seatOccupancy(q,1);
//
//        System.out.println("omg "+ret);
//
//        ret = seatService.seatOccupancy(q,1);
//
//        System.out.println("omg "+ret);

    }
}