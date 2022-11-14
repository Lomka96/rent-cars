package com.upskill.rentcars;

import com.upskill.rentcars.model.db.Customer;
import com.upskill.rentcars.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveCustomerTest(){

        Customer customer = Customer.builder()

                .firstName("TestName")
                .lastName("TestLastName")
                .driverLicense("202201")
                .email("Test@gmail.com")
                .build();

        customerRepository.save(customer);

        Assertions.assertThat(customer.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getCustomerTest(){
        Customer customer = customerRepository.findById(1L).get();

        Assertions.assertThat(customer.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    public void getListOfCustomersTest(){

        List<Customer> customers = customerRepository.findAll();

        Assertions.assertThat(customers.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateCustomerTest(){
        Customer customer = customerRepository.findById(1L).get();
        customer.setDriverLicense("990099");
        Customer customerUpdated = customerRepository.save(customer);

        Assertions.assertThat(customerUpdated.getDriverLicense()).isEqualTo("990099");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteCustomerTest(){
        Customer customer = customerRepository.findById(1L).get();
        customerRepository.delete(customer);
        Customer customer1 = null;
        Optional<Customer> optionalCustomer = customerRepository.findByDriverLicense("990099");
        if(optionalCustomer.isPresent()){
            customer1 = optionalCustomer.get();
        }

        Assertions.assertThat(customer1).isNull();
    }
}
