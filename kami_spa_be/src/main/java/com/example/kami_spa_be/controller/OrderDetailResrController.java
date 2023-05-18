package com.example.kami_spa_be.controller;

import com.example.kami_spa_be.model.OrderDetail;
import com.example.kami_spa_be.model.Orders;
import com.example.kami_spa_be.model.Person;
import com.example.kami_spa_be.service.IOrderDetailService;
import com.example.kami_spa_be.service.IOrdersService;
import com.example.kami_spa_be.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user/order-detail")
public class OrderDetailResrController {
    @Autowired
    private IPersonService iPersonService;
    @Autowired
    private IOrdersService iOrdersService;
    @Autowired
    private IOrderDetailService iOrderDetailService;
    @GetMapping("/cart")
    public ResponseEntity<List<OrderDetail>> getCart(@RequestParam(defaultValue = "", required = false) String username) {
        Person person = this.iPersonService.findByEmail(username);
        Orders orders = this.iOrdersService.findOrderByPersonId(person.getId());
        if (orders == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<OrderDetail> orderDetails = this.iOrderDetailService.findAllByOrderId(orders.getId());
        if (orderDetails == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }
    @GetMapping("/total")
    public ResponseEntity<List<Double>> getTotal(@RequestParam(defaultValue = "", required = false) String username) {
        Person person = this.iPersonService.findByEmail(username);
        Orders orders = this.iOrdersService.findOrderByPersonId(person.getId());
        if (orders == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<OrderDetail> orderDetails = this.iOrderDetailService.findAllByOrderId(orders.getId());
        if (orderDetails == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<Double> totalAndQuantity = new ArrayList<>();
        double total = 0;
        double quantity = 0;
        for (OrderDetail item :orderDetails) {
            total += item.getProduct().getPrice() * item.getOrderedQuantity();
            quantity += item.getOrderedQuantity();
        }
        totalAndQuantity.add(total);
        totalAndQuantity.add(quantity);
        return new ResponseEntity<>(totalAndQuantity, HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam(defaultValue = "0") Long id){
        OrderDetail orderDetail = this.iOrderDetailService.findOrderDetail(id);
        if (orderDetail == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        this.iOrderDetailService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
