package com.upskill.rentcars.service;

import com.upskill.rentcars.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getCustomers();

    Customer getCustomer(Long id);

    Customer addNewCustomer(Customer customer);

    boolean deleteCustomer(Long id);

    Customer updateCustomer(Long id, Customer updateCustomer);

    List<Customer> list(int limit);
}
