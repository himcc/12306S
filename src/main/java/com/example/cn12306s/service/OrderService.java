package com.example.cn12306s.service;

import com.example.cn12306s.entity.OrderEntity;

import java.util.List;

public interface OrderService {

    public int paySuccess(long id);
    public List<OrderEntity> getOrderSByUid(long uid);

    public void refundTicket(long orderId);

    public void deleteOrder(long id);

}
