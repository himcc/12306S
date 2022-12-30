package com.example.cn12306s.service.imp;

import com.example.cn12306s.dao.OrderDAO;
import com.example.cn12306s.dao.SeatDAO;
import com.example.cn12306s.entity.OrderEntity;
import com.example.cn12306s.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private SeatDAO seatDAO;

    @Override
    public int paySuccess(long id) {
        return orderDAO.paySuccess(id);
    }

    @Override
    public List<OrderEntity> getOrderSByUid(long uid) {
        return orderDAO.getOrderSByUid(uid);
    }

    @Override
    public void refundTicket(long orderId) {
        int x1 = orderDAO.refundTicket(orderId);
        OrderEntity order = orderDAO.getOrderById(orderId);
        int x2 = seatDAO.refundSeat(order);
    }
}
