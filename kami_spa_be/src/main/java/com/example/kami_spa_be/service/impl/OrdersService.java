package com.example.kami_spa_be.service.impl;

import com.example.kami_spa_be.model.Orders;
import com.example.kami_spa_be.repository.IOrdersRepository;
import com.example.kami_spa_be.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService implements IOrdersService {
    @Autowired
    private IOrdersRepository iOrdersRepository;
    @Override
    public void save(Orders orders) {
        this.iOrdersRepository.save(orders);
    }

    @Override
    public Orders findOrderByPersonId(Long id) {
        return this.iOrdersRepository.findOrdersByPersonId(id);
    }

    @Override
    public boolean update(Long id, double total) {
        Orders orders = this.iOrdersRepository.findById(id).get();
        if (orders == null){
            return false;
        }
        orders.setPaid(true);
        orders.setTotal(total);
        this.iOrdersRepository.save(orders);
        return true;
    }
}
