package com.upskill.rentcars.service;

import com.upskill.rentcars.model.db.Car;
import com.upskill.rentcars.model.db.Orders;
import com.upskill.rentcars.model.dto.OrderEditRequest;
import com.upskill.rentcars.model.dto.OrderRequest;
import com.upskill.rentcars.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
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
    public List<Orders> getOrders() {
        return orderRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Orders::getStartDate))
                .collect(Collectors.toList());
    }

    @Override
    public Orders getOrder(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public Orders addNewOrder(OrderRequest orderRequest, Long carId) {
        Car car = carService.findCarById(carId).get();
        Orders orders = new Orders();
        orders.setCar(car);
        orders.setCustomer(customerService.findCustomerOrCreate(orderRequest));
        orders.setStartDate(orderRequest.getStartDate());
        orders.setFinishDate(orderRequest.getFinishDate());
        return orderRepository.save(orders);
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
                .map(Orders::getCar)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Orders updateOrder(Long id, OrderEditRequest orderEditRequest) {
        Orders orders = orderRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("order with id " + id + " does not exists"));
        if(isFieldSet(orderEditRequest.getFirstName())) {
            orders.getCustomer().setFirstName(orderEditRequest.getFirstName());
        }
        if(isFieldSet(orderEditRequest.getLastName())) {
            orders.getCustomer().setLastName(orderEditRequest.getLastName());
        }
        if(isFieldSet(orderEditRequest.getDriverLicense())) {
            orders.getCustomer().setDriverLicense(orderEditRequest.getDriverLicense());
        }
        if(isFieldSet(orderEditRequest.getEmail())) {
            orders.getCustomer().setEmail(orderEditRequest.getEmail());
        }
        if(isFieldSet(orderEditRequest.getModel())) {
            orders.getCar().setModel(orderEditRequest.getModel());
        }
        if(isFieldSet(orderEditRequest.getColor())) {
            orders.getCar().setColor(orderEditRequest.getColor());
        }
        if(isField(orderEditRequest.getYear())) {
            orders.getCar().setYear(orderEditRequest.getYear());
        }
        if(isFieldSet(orderEditRequest.getVinId())) {
            orders.getCar().setVinId(orderEditRequest.getVinId());
        }
        if(isField(orderEditRequest.getCostPerDay())) {
            orders.getCar().setCostPerDay(orderEditRequest.getCostPerDay());
        }
        if(isField(orderEditRequest.getStartDate())) {
            orders.setStartDate(orderEditRequest.getStartDate());
        }
        if(isField(orderEditRequest.getFinishDate())) {
            orders.setFinishDate(orderEditRequest.getFinishDate());
        }
        return orderRepository.save(orders);
    }

    public boolean isField(long field) {
        return (field != 0);
    }
    public boolean isField(int field) {
        return (field != 0);
    }
    public boolean isFieldSet(String field) {
        return !(field == null || field.isEmpty());
    }
    public boolean isField(Date date) {
        return (date != null);
    }
}
