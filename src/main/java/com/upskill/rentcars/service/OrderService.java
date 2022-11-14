package com.upskill.rentcars.service;

import com.upskill.rentcars.model.db.Car;
import com.upskill.rentcars.model.db.Orders;
import com.upskill.rentcars.model.dto.OrderEditRequest;
import com.upskill.rentcars.model.dto.OrderRequest;

import java.util.List;

public interface OrderService {

    List<Orders> getOrders();

    Orders getOrder(Long id);

    Orders addNewOrder(OrderRequest orderRequest, Long carId);

    boolean deleteOrder(Long id);

    boolean deleteOrderByCarId(Long carId);

    boolean deleteOrderByCustomerId(Long id);

    List<Car> getCustomerCars(Long id);

    Orders updateOrder(Long id, OrderEditRequest orderEditRequest);
}
