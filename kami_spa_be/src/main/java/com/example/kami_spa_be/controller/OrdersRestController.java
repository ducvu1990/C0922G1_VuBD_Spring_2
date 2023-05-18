package com.example.kami_spa_be.controller;

import com.example.kami_spa_be.dto.OrderRequestDTO;
import com.example.kami_spa_be.dto.PayDTO;
import com.example.kami_spa_be.model.OrderDetail;
import com.example.kami_spa_be.model.Orders;
import com.example.kami_spa_be.model.Person;
import com.example.kami_spa_be.model.Product;
import com.example.kami_spa_be.service.IOrderDetailService;
import com.example.kami_spa_be.service.IOrdersService;
import com.example.kami_spa_be.service.IPersonService;
import com.example.kami_spa_be.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user/order")
public class OrdersRestController {
    @Autowired
    private IOrdersService iOrdersService;
    @Autowired
    private IOrderDetailService iOrderDetailService;
    @Autowired
    private IPersonService iPersonService;
    @Autowired
    private IProductService iProductService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody OrderRequestDTO orderRequestDTO) {
        Person person = this.iPersonService.findByEmail(orderRequestDTO.getEmail());
        Orders orders = this.iOrdersService.findOrderByPersonId(person.getId());
        Product product = this.iProductService.findProduct(orderRequestDTO.getProductId());
        if (product.getProductQuantity() <= 0 || product == null) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
        if (orders == null) {
            Orders orders1 = new Orders();
            orders1.setEmployeeId(1L);
            orders1.setPaid(false);
            Date date = new Date();
            orders1.setOrder_date(date);

            orders1.setPerson(person);
            orders1.setOrderCode("Kh" + date);
            this.iOrdersService.save(orders1);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrders(orders1);
            orderDetail.setOrderedQuantity(orderRequestDTO.getQuantity());

            orderDetail.setProduct(product);
            this.iOrderDetailService.save(orderDetail);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        OrderDetail orderDetail = this.iOrderDetailService.findOrderDetailByProductIdAndOrderId(product.getId(), orders.getId());
        if (orderDetail == null) {
            OrderDetail orderDetail1 = new OrderDetail();
            orderDetail1.setOrders(orders);
            orderDetail1.setOrderedQuantity(orderRequestDTO.getQuantity());
            orderDetail1.setProduct(product);
            this.iOrderDetailService.save(orderDetail1);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        orderDetail.setOrderedQuantity(orderRequestDTO.getQuantity());
        this.iOrderDetailService.save(orderDetail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/quantity")
    public ResponseEntity<Integer> getQuantity(@RequestParam(defaultValue = "", required = false) String username) {
        Person person = this.iPersonService.findByEmail(username);
        Orders orders = this.iOrdersService.findOrderByPersonId(person.getId());
        if (orders == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<OrderDetail> orderDetails = this.iOrderDetailService.findAllByOrderId(orders.getId());
        if (orderDetails == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        int quantity = orderDetails.size();
        return new ResponseEntity<>(quantity, HttpStatus.OK);
    }
    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestBody PayDTO payDTO){
        Person person = this.iPersonService.findByEmail(payDTO.getEmail());
        Orders orders = this.iOrdersService.findOrderByPersonId(person.getId());
        List<OrderDetail> orderDetails = this.iOrderDetailService.findAllByOrderId(orders.getId());
        for (OrderDetail item : orderDetails) {
            if (item.getProduct().getProductQuantity() > this.iProductService.findProduct(item.getProduct().getId()).getProductQuantity()){
                return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
            }
        }
        for (OrderDetail item : orderDetails) {
            if (!this.iProductService.update(item.getProduct().getId(), item.getOrderedQuantity())){
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }

        }
        this.iOrderDetailService.update(orders.getId());
        this.iOrdersService.update(orders.getId(), payDTO.getTotal());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
