package com.upskill.rentcars.controller;

import com.upskill.rentcars.model.Customer;
import com.upskill.rentcars.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable("id") Long id){
        return customerService.getCustomer(id);
    }

    @PostMapping("/save")
    public void registerNewCustomer(@RequestBody Customer customer){
        customerService.addNewCustomer(customer);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomerById(@PathVariable("id") Long id){
        customerService.deleteCustomer(id);
    }

    @PatchMapping("/update/{id}")
    public Customer updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }
}
