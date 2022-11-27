package com.upskill.rentcars.service;

import com.upskill.rentcars.model.db.Customer;
import java.util.Arrays;

import com.upskill.rentcars.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {
    public static final Customer CUSTOMER_1 = new Customer( 1L, "Ivan", "Ivanov",
            "2022100", "Ivan_Ivanov@gmail.com");
    public static final Customer CUSTOMER_2 = new Customer( 2L, "Petr", "Petrov",
            "2022101", "Petr_Petrov@gmail.com");
    public static final Customer CUSTOMER_3 = new Customer( 3L, "Andrey", "Andreev",
            "2022102", "Andrey_Andreev@gmail.com");
    public static final List<Customer> CUSTOMERS_LIST = Arrays.asList(CUSTOMER_1, CUSTOMER_2, CUSTOMER_3);

    @Mock
    private CustomerRepository customerRepository;
    private CustomerServiceImpl customerService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    public void whenGetCustomers_thenRetrievedCorrectValue() {
        Mockito.when(customerRepository.findAll()).thenReturn(CUSTOMERS_LIST);
        List<Customer> testCustomerList = customerService.getCustomers();
        Assert.assertEquals(CUSTOMERS_LIST, testCustomerList);
    }

    @Test
    public void whenGetExistingCustomer_thenRetrievedCorrectValue() {
        long id = 2L;
        Mockito.when(customerRepository.findById(id)).thenReturn(Optional.of(CUSTOMER_2));
        Customer testCustomer = customerService.getCustomer(id);
        Assert.assertEquals(CUSTOMER_2, testCustomer);
    }

    @Test(expected = RuntimeException.class)
    public void whenGetNonexistentCustomer_thenExceptionThrows() {
        long id = 5L;
        Mockito.when(customerRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        Customer testCustomer = customerService.getCustomer(id);
    }

    @Test
    public void whenAddNewCustomer_thenCustomerAdded() {
        Mockito.when(customerRepository.findByDriverLicense(CUSTOMER_2.getDriverLicense())).thenReturn(Optional.of(CUSTOMER_2));
        Customer testCustomer = customerService.addNewCustomer(CUSTOMER_2);
        Assert.assertEquals(CUSTOMER_2, testCustomer);
    }

    @Test
    public void whenDeleteCustomer_thenCustomerDeleted() {
        Mockito.when(customerRepository.existsById(CUSTOMER_1.getId())).thenReturn(true);
        Assert.assertEquals(true, customerService.deleteCustomer(CUSTOMER_1.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenDeleteCustomerWithNotExistingId_thenExceptionThrows() {
        Mockito.when(customerRepository.existsById(6L)).thenReturn(false);
        customerService.deleteCustomer(6L);
    }

    @Test
    public void whenUpdateCustomer_thenCustomerUpdated() {
        CustomerServiceImpl spyCustomerService = Mockito.spy(customerService);
        Customer updatedCustomer = new Customer("Ivan", "Ivanov",
                "2022555", "Ivan_Ivanov@gmail.com");
        Mockito.when(customerRepository.findById(CUSTOMER_1.getId())).thenReturn(Optional.of(CUSTOMER_1));
        spyCustomerService.updateCustomer(1L, updatedCustomer);
        Mockito.verify(spyCustomerService, times(4)).isFieldSet(anyString());
    }

    @Test(expected = IllegalStateException.class)
    public void whenUpdateCustomerWithNotExistingId_thenExceptionThrows() {
        Mockito.when(customerRepository.findById(6L)).thenReturn(Optional.ofNullable(null));
        customerService.updateCustomer(6L, CUSTOMER_3);
    }
}
