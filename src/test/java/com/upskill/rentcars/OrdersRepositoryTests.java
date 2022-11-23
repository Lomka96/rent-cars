
package com.upskill.rentcars;

import com.upskill.rentcars.model.db.Order;
import com.upskill.rentcars.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class OrdersRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder() throws ParseException {
        SimpleDateFormat changeFormat = new SimpleDateFormat("yyyy-MM-dd");

        Order order = Order.builder()
                .id(1L)
                .cost(50)
                .startDate(changeFormat.parse("2022-01-01"))
                .finishDate(changeFormat.parse("2022-02-01"))
                .build();
        return order;
    }

    @Test
    public void saveOrderTest() throws ParseException {

        orderRepository.save(createOrder());

        Assertions.assertThat(createOrder().getId()).isGreaterThan(0);
    }

    @Test
    public void getCustomerTest() throws ParseException {

        orderRepository.save(createOrder());

        Assertions.assertThat(createOrder().getId()).isEqualTo(1L);
    }

    @Test
    public void getListOfOrdersTest() throws ParseException {

        orderRepository.save(createOrder());
        List<Order> orders = orderRepository.findAll();

        Assertions.assertThat(orders.size()).isGreaterThan(0);
    }

    @Test
    public void updateOrderTest() throws ParseException {
        SimpleDateFormat changeFormat = new SimpleDateFormat("yyyy-MM-dd");
        Order order = Order.builder()
                .id(1L)
                .cost(50)
                .startDate(changeFormat.parse("2022-01-01"))
                .finishDate(changeFormat.parse("2022-02-01"))
                .build();

        order.setCost(55);
        Order orderUpdated = orderRepository.save(order);

        Assertions.assertThat(orderUpdated.getCost()).isEqualTo(55);
    }

    @Test
    public void deleteCustomerTest() throws ParseException {

        orderRepository.delete(createOrder());
        Order order1 = null;
        Optional<Order> optionalOrder = orderRepository.findById(1L);
        if(optionalOrder.isPresent()){
            order1 = optionalOrder.get();
        }

        Assertions.assertThat(order1).isNull();
    }
}

