package com.upskill.rentcars;

import com.upskill.rentcars.model.db.Car;
import com.upskill.rentcars.model.db.Customer;
import com.upskill.rentcars.model.db.Orders;
import com.upskill.rentcars.repository.CustomerRepository;
import com.upskill.rentcars.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrdersRepositoryTests {

    private Car car = new Car();

    @Autowired
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;

    Customer customer = Customer.builder()

            .firstName("TestName")
            .lastName("TestLastName")
            .driverLicense("202201")
            .email("Test@gmail.com")
            .build();



    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveOrderTest() throws ParseException {

        SimpleDateFormat changeFormat = new SimpleDateFormat("yyyy-MM-dd");

        Orders orders = Orders.builder()
                .id(1L)
                //.car(car)
                //.customer(customer)
                .cost(50)
                .startDate(changeFormat.parse("2022-01-01"))
                .finishDate(changeFormat.parse("2022-02-01"))
                .build();

        orderRepository.save(orders);

        Assertions.assertThat(orders.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getCustomerTest(){
        Orders orders = orderRepository.findById(1L).get();

        Assertions.assertThat(orders.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    public void getListOfOrdersTest(){

        List<Orders> orders = orderRepository.findAll();

        Assertions.assertThat(orders.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateOrderTest(){
        Orders orders = orderRepository.findById(1L).get();
        orders.setCost(55);
        Orders orderUpdated = orderRepository.save(orders);

        Assertions.assertThat(orderUpdated.getCost()).isEqualTo(55);
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteCustomerTest(){
        Orders orders = orderRepository.findById(1L).get();
        orderRepository.delete(orders);
        Orders orders1 = null;
        Optional<Orders> optionalOrders = orderRepository.findById(1L);
        if(optionalOrders.isPresent()){
            orders1 = optionalOrders.get();
        }

        Assertions.assertThat(orders1).isNull();
    }
}
