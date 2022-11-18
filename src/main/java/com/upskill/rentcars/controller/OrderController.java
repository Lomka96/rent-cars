package com.upskill.rentcars.controller;

import com.upskill.rentcars.model.db.Order;
import com.upskill.rentcars.model.dto.OrderEditRequest;
import com.upskill.rentcars.model.dto.OrderRequest;
import com.upskill.rentcars.service.CustomerService;
import com.upskill.rentcars.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    @Autowired
    public OrderController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @GetMapping
    public List<Order> getOrders(){
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable("id") Long id){
        return orderService.getOrder(id);
    }

    @PostMapping("/{carId}")
    public Order registerNewOrder(@RequestBody OrderRequest orderRequest,
                                  @PathVariable("carId") Long carId){

        return orderService.addNewOrder(orderRequest, carId);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderById(@PathVariable("id") Long id){
        orderService.deleteOrder(id);
    }

    @PatchMapping("/{id}")
    public Order updateOrder(@PathVariable("id") Long id, @RequestBody OrderEditRequest orderEditRequest) {
        // OrderEditRequest -> Order
        return orderService.updateOrder(id, orderEditRequest);
    }
}
