package com.upskill.rentcars.service;

import com.upskill.rentcars.model.db.Car;
import com.upskill.rentcars.model.db.Customer;
import com.upskill.rentcars.model.db.Order;
import com.upskill.rentcars.model.dto.OrderEditRequest;
import com.upskill.rentcars.model.dto.OrderRequest;
import com.upskill.rentcars.repository.CarRepository;
import com.upskill.rentcars.repository.CustomerRepository;
import com.upskill.rentcars.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    public static final Car CAR_1 = new Car(1L, "BMW X6", "Blue", 2019, "599620",
            "", 66);
    public static final Car CAR_2 = new Car(2L, "Opel Astra", "White", 2015, "985467",
            "", 66);
    public static final Customer CUSTOMER_1 = new Customer( 1L, "Ivan", "Ivanov",
            "2022100", "Ivan_Ivanov@gmail.com");
    public static final Customer CUSTOMER_2 = new Customer( 2L, "Petr", "Petrov",
            "2022101", "Petr_Petrov@gmail.com");
    public static final Order ORDER_1 = new Order(null, CUSTOMER_1, CAR_1, convertIntToDate(150000), convertIntToDate(160000), 0);
    public static final Order ORDER_2 = new Order(2L, CUSTOMER_2, CAR_2, convertIntToDate(165000), convertIntToDate(175000), 88);
    public static final List<Order> ORDERS_LIST = Arrays.asList(ORDER_1, ORDER_2);
    public static final OrderRequest ORDER_REQUEST = new OrderRequest(CUSTOMER_1.getFirstName(), CUSTOMER_1.getLastName(),
            CUSTOMER_1.getDriverLicense(), CUSTOMER_1.getEmail(), ORDER_1.getStartDate(), ORDER_1.getFinishDate());
    public static final OrderEditRequest ORDER_EDIT_REQUEST = new OrderEditRequest(1L, CUSTOMER_1.getFirstName(), CUSTOMER_1.getLastName(),
            CUSTOMER_1.getDriverLicense(), CUSTOMER_1.getEmail(), CAR_1.getModel(), CAR_1.getColor(), CAR_1.getYear(),
            CAR_1.getVinId(), CAR_1.getCostPerDay(), ORDER_1.getStartDate(), ORDER_1.getFinishDate());

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CarRepository carRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerServiceImpl customerService;
    private OrderServiceImpl orderService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository);
        orderService = new OrderServiceImpl(orderRepository, carRepository, customerService);
    }

    @Test
    public void whenGetOrders_thenRetrievedCorrectValue() {
        Mockito.when(orderRepository.findAll()).thenReturn(ORDERS_LIST);
        List<Order> testOrderList = orderService.getOrders();
        Assert.assertEquals(ORDERS_LIST, testOrderList);
    }

    @Test
    public void whenGetExistingOrder_thenRetrievedCorrectValue() {
        long id = 2L;
        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.of(ORDER_2));
        Order testOrder = orderService.getOrder(id);
        Assert.assertEquals(ORDER_2, testOrder);
    }

    @Test(expected = RuntimeException.class)
    public void whenGetNonexistentOrder_thenExceptionThrows() {
        long id = 5L;
        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        orderService.getOrder(id);
    }

    @Test
    public void whenAddNewOrder_thenOrderAdded() {
        Mockito.when(carRepository.findById(CAR_1.getId())).thenReturn(Optional.of(CAR_1));
        Mockito.when(customerService.findCustomerOrCreate(ORDER_REQUEST)).thenReturn(CUSTOMER_1);
        Mockito.when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);
        Order testOrder = orderService.addNewOrder(ORDER_REQUEST, CAR_1.getId());
        Assert.assertEquals(ORDER_1, testOrder);
    }

    @Test
    public void whenDeleteOrder_thenOrderDeleted() {
        Mockito.doNothing().when(orderRepository).deleteById(ORDER_2.getId());
        Assert.assertTrue(orderService.deleteOrder(ORDER_2.getId()));
    }

    @Test
    public void whenDeleteOrderByCarId_thenOrderDeleted() {
        Mockito.doNothing().when(orderRepository).deleteByCarId(CAR_2.getId());
        Assert.assertTrue(orderService.deleteOrderByCarId(ORDER_2.getCar().getId()));
    }

    @Test
    public void whenDeleteOrderByCustomerId_thenOrderDeleted() {
        Mockito.doNothing().when(orderRepository).deleteByCustomerId(CUSTOMER_2.getId());
        Assert.assertTrue(orderService.deleteOrderByCustomerId(ORDER_2.getCustomer().getId()));
    }

    @Test
    public void whenGetCustomerCars_thenRetrievedCorrectValue() {
        List<Car> CustomerCarsList = orderService.getOrders().stream().
                filter(order -> order.getCustomer().getId().equals(CUSTOMER_1.getId()))
                .map(Order::getCar)
                .distinct()
                .collect(Collectors.toList());
        List<Car> testCarList = orderService.getCustomerCars(CUSTOMER_1.getId());
        Assert.assertEquals(CustomerCarsList, testCarList);
    }

    @Test
    public void whenUpdateOrder_thenOrderUpdated() {
        OrderServiceImpl spyOrderService = Mockito.spy(orderService);
        OrderEditRequest updatedOrderEditRequest = new OrderEditRequest(2L, CUSTOMER_2.getFirstName(), CUSTOMER_2.getLastName(),
                CUSTOMER_2.getDriverLicense(), CUSTOMER_2.getEmail(), CAR_2.getModel(), CAR_2.getColor(), CAR_2.getYear(),
                CAR_2.getVinId(), CAR_2.getCostPerDay(), ORDER_1.getStartDate(), ORDER_1.getFinishDate());
        Mockito.when(orderRepository.findById(ORDER_2.getId())).thenReturn(Optional.of(ORDER_2));
        spyOrderService.updateOrder(2L, updatedOrderEditRequest);
        Mockito.verify(spyOrderService, times(7)).isFieldSet(anyString());
        Mockito.verify(spyOrderService, times(2)).isField(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void whenUpdateOrderWithNotExistingId_thenExceptionThrows() {
        Mockito.when(orderRepository.findById(6L)).thenReturn(Optional.ofNullable(null));
        orderService.updateOrder(6L, ORDER_EDIT_REQUEST);
    }

    public static Date convertIntToDate(Integer intDate) {
        int intYear = intDate/100;
        int intMonth = intDate - (intYear * 100);
        Calendar result = new GregorianCalendar();
        result.set(intYear, intMonth - 1, 1, 0, 0, 0);
        return result.getTime();
    }
}
