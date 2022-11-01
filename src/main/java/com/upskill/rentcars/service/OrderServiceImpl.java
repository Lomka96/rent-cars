package com.upskill.rentcars.service;

import com.upskill.rentcars.model.db.Car;
import com.upskill.rentcars.model.db.Customer;
import com.upskill.rentcars.model.db.Order;
import com.upskill.rentcars.model.dto.OrderRequest;
import com.upskill.rentcars.repository.CarRepository;
import com.upskill.rentcars.repository.CustomerRepository;
import com.upskill.rentcars.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final CarService carService;
    private final CustomerService customerService;

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public Order addNewOrder(OrderRequest orderRequest, Long carId) {
        Car car = carService.findCarById(carId).get();
        Order order = new Order();
        order.setCar(car);
        order.setCustomer(customerService.findCustomerOrCreate(orderRequest));
        order.setStartDate(orderRequest.getStartDate());
        order.setFinishDate(orderRequest.getFinishDate());
        return orderRepository.save(order);
    }

    @Override
    public boolean deleteOrder(Long id) {
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean deleteOrderByCarId(Long carId) {
        orderRepository.deleteByCarId(carId);
        return true;
    }

    @Override
    public boolean deleteOrderByCustomerId(Long id) {
        orderRepository.deleteByCustomerId(id);
        return true;
    }

    @Override
    public List<Car> getCustomerCars(Long id) {
        return getOrders().stream()
                .filter(order -> order.getCustomer().getId().equals(id))
                .map(Order::getCar)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Order updateOrder(Long id, Order updateOrder) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("order with id " + id + " does not exists"));
//        if(isField(updateOrder.getCar().getId())) {
//            order.setCar().getId(updateOrder.getCar().getId());
//        }
//        if(isField(updateOrder.getCustomerId())) {
//            order.setCustomerId(updateOrder.getCustomerId());
//        }
//        if(isField(updateOrder.getStartDate())) {
//            order.setStartDate(updateOrder.getStartDate());
//        }
//        if(isField(updateOrder.getFinishDate())) {
//            order.setFinishDate(updateOrder.getFinishDate());
//        }
//        if(isField(updateOrder.getCost())) {
//            order.setCost(updateOrder.getCost());
//        }
        return orderRepository.save(order);
    }

    public boolean isField(long field) {
        return (field != 0);
    }
    public boolean isField(double field) {
        return (field != 0);
    }
    public boolean isField(Date date) {
        return (date != null);
    }
}
