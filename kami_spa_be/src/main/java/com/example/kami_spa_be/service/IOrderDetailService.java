package com.example.kami_spa_be.service;

import com.example.kami_spa_be.model.OrderDetail;
import org.hibernate.criterion.Order;

import java.util.List;

public interface IOrderDetailService {
    void save(OrderDetail orderDetail);
    OrderDetail findOrderDetailByProductIdAndOrderId(Long productId, Long ordersId);
    List<OrderDetail> findAllByOrderId(Long id);
    void remove(Long id);
    OrderDetail findOrderDetail(Long id);
    boolean update(Long id);
}
