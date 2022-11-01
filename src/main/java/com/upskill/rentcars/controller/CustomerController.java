package com.upskill.rentcars.controller;

import com.upskill.rentcars.model.db.Car;
import com.upskill.rentcars.model.db.Customer;
import com.upskill.rentcars.service.CustomerService;
import com.upskill.rentcars.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final OrderService orderService;

    @Autowired
    public CustomerController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable("id") Long id){
        return customerService.getCustomer(id);
    }

    @GetMapping("/{id}/cars")
    public List<Car> getCustomerCars(@PathVariable("id") Long id){
        return orderService.getCustomerCars(id);
    }

    @PostMapping
    public void registerNewCustomer(@RequestBody Customer customer){
        customerService.addNewCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable("id") Long id){
        orderService.deleteOrderByCustomerId(id);
        customerService.deleteCustomer(id);
    }

    @PatchMapping("/{id}")
    public Customer updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

}
