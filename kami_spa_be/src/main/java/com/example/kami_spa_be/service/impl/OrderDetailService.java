package com.example.kami_spa_be.service.impl;

import com.example.kami_spa_be.model.OrderDetail;
import com.example.kami_spa_be.repository.IOrderDetailRepository;
import com.example.kami_spa_be.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService implements IOrderDetailService {
    @Autowired
    private IOrderDetailRepository iOrderDetailRepository;
    @Override
    public void save(OrderDetail orderDetail) {
        this.iOrderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail findOrderDetailByProductIdAndOrderId(Long productId, Long ordersId) {
        return this.iOrderDetailRepository.findOrderDetailByProductIdAndOrders_Id(productId, ordersId);
    }

    @Override
    public List<OrderDetail> findAllByOrderId(Long id) {
        return this.iOrderDetailRepository.findAllByOrderByIdAndUnpaid(id);
    }

    @Override
    public void remove(Long id) {
        this.iOrderDetailRepository.deleteById(id);
    }

    @Override
    public OrderDetail findOrderDetail(Long id) {
        return this.iOrderDetailRepository.findById(id).get();
    }

    @Override
    public boolean update(Long id) {
        List<OrderDetail> orderDetails = this.iOrderDetailRepository.findAllByOrderByIdAndUnpaid(id);
        if (orderDetails == null){
            return false;
        }
        for (OrderDetail item : orderDetails) {
            item.setPay(true);
            this.iOrderDetailRepository.save(item);
        }
        return true;
    }
}
