package com.upskill.rentcars.service;

import com.upskill.rentcars.model.db.Car;
import com.upskill.rentcars.model.db.Order;
import com.upskill.rentcars.model.dto.OrderEditRequest;
import com.upskill.rentcars.model.dto.OrderRequest;

import java.util.List;

public interface OrderService {

    List<Order> getOrders();

    Order getOrder(Long id);

    Order addNewOrder(OrderRequest orderRequest, Long carId);

    boolean deleteOrder(Long id);

    boolean deleteOrderByCarId(Long carId);

    boolean deleteOrderByCustomerId(Long id);

    List<Car> getCustomerCars(Long id);

    Order updateOrder(Long id, OrderEditRequest orderEditRequest);
}
