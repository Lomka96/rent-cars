package com.upskill.rentcars.service;

import com.upskill.rentcars.model.db.Car;
import com.upskill.rentcars.model.db.Customer;
import com.upskill.rentcars.model.dto.OrderRequest;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> getCustomers();

    Customer getCustomer(Long id);

    Customer addNewCustomer(Customer customer);

    boolean deleteCustomer(Long id);

    Customer updateCustomer(Long id, Customer updateCustomer);

    Optional<Customer> findCustomerByDriverLicense(String driverLicense);

    Customer findCustomerOrCreate(OrderRequest orderRequest);
}
