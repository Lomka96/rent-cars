package com.upskill.rentcars.service;

import com.upskill.rentcars.model.db.Customer;
import com.upskill.rentcars.model.dto.OrderRequest;
import com.upskill.rentcars.repository.CarRepository;
import com.upskill.rentcars.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(Long id) {
        log.info("Fetching customer by id: {}", id);
        return customerRepository.findById(id).get();
    }

    @Override
    public Customer addNewCustomer(Customer customer) {
        log.info("Saving a new customer with driver license: {}", customer.getDriverLicense());
        if (findCustomerByDriverLicense(customer.getDriverLicense()).isPresent()) {
            return customer;
        }
        return customerRepository.save(customer);
    }

    @Override
    public boolean deleteCustomer(Long id) {
        boolean exists = customerRepository.existsById(id);
        if(!exists){
            throw new IllegalArgumentException("Customer with id " + id + " does not exists");
        }
        log.info("Deleting a customer with id: {}", id);
        customerRepository.deleteById(id);
        return true;
    }

    @Override
    public Customer updateCustomer(Long id, Customer updateCustomer) {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("customer with id " + id + " does not exists"));

        if (isFieldSet(updateCustomer.getFirstName())) {
            customer.setFirstName(updateCustomer.getFirstName());
        }
        if (isFieldSet(updateCustomer.getLastName())) {
            customer.setLastName(updateCustomer.getLastName());
        }
        if (isFieldSet(updateCustomer.getDriverLicense())) {
            customer.setDriverLicense(updateCustomer.getDriverLicense());
        }
        if (isFieldSet(updateCustomer.getEmail())) {
            customer.setEmail(updateCustomer.getEmail());
        }
        log.info("Updating a customer with driver license: {}", customer.getDriverLicense());
        return customerRepository.save(customer);
    }

    public boolean isFieldSet(String field) {
        return !(field == null || field.isEmpty());
    }

    @Override
    public List<Customer> list(int limit) {
        log.info("Fetching all customers");
        return customerRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Optional<Customer> findCustomerByDriverLicense(String driverLicense){
        return customerRepository.findByDriverLicense(driverLicense);
    }

    public Customer findCustomerOrCreate(OrderRequest orderRequest) {
        return findCustomerByDriverLicense(orderRequest.getDriverLicense())
                .orElse(addNewCustomer(
                        new Customer(orderRequest.getFirstName(),
                                orderRequest.getLastName(),
                                orderRequest.getDriverLicense(),
                                orderRequest.getEmail())));
    }
}
