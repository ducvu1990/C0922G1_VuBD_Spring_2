package com.example.kami_spa_be.repository;

import com.example.kami_spa_be.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    OrderDetail findOrderDetailByProductIdAndOrders_Id(Long productId, Long ordersId);
    @Query(value = "select * from order_detail where is_pay = 0 and order_id = ?1",nativeQuery = true)
    List<OrderDetail> findAllByOrderByIdAndUnpaid(Long id);
}
