package com.example.kami_spa_be.service;

import com.example.kami_spa_be.model.Orders;

public interface IOrdersService {
    void save(Orders orders);
    Orders findOrderByPersonId(Long id);
    boolean update(Long id, double total);
}
