package com.upskill.rentcars.service;

import com.upskill.rentcars.model.Customer;
import com.upskill.rentcars.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerServiceImpl implements com.upskill.rentcars.service.CustomerService{

    private final CustomerRepository customerRepository;

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
        Optional<Customer> customerByDriverLicense = customerRepository.findCustomerByDriverLicense(customer.getDriverLicense());
        if(customerByDriverLicense.isPresent()) {
            throw new IllegalArgumentException("Customer with this driver license already exists");
        }
        log.info("Saving a new customer with driver license: {}", customer.getDriverLicense());
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
        if (isField(updateCustomer.getDriverLicense())) {
            customer.setDriverLicense(updateCustomer.getDriverLicense());
        }
        if (isFieldSet(updateCustomer.getEmail())) {
            customer.setEmail(updateCustomer.getEmail());
        }
        if (isFieldDate(updateCustomer.getStartDate())) {
            customer.setStartDate(updateCustomer.getStartDate());
        }
        if (isFieldDate(updateCustomer.getFinishDate())) {
            customer.setFinishDate(updateCustomer.getFinishDate());
        }
        log.info("Updating a customer with driver license: {}", customer.getDriverLicense());
        return customerRepository.save(customer);
    }

    public boolean isFieldSet(String field) {
        return !(field == null || field.isEmpty());
    }
    public boolean isFieldDate(LocalDate dateTime) {
        return !(dateTime == null);
    }
    public boolean isField(int field) {
        return (field != 0);
    }

    @Override
    public List<Customer> list(int limit) {
        log.info("Fetching all customers");
        return customerRepository.findAll(PageRequest.of(0, limit)).toList();
    }
}
