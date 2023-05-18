package com.example.kami_spa_be.repository;

import com.example.kami_spa_be.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IOrdersRepository extends JpaRepository<Orders, Long> {

    @Query(value = "SELECT * FROM orders o WHERE is_paid = 0 AND person_id = ?1", nativeQuery = true)
    Orders findOrdersByPersonId(Long id);
}
