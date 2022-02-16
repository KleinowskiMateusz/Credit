package com.example.credit.customer;

import com.example.credit.credit.Credit;
import com.example.credit.credit.CreditRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;
    private CustomerController controllerUnderTest;

    @BeforeEach
    void setUp() {
        controllerUnderTest = new CustomerController(customerService);
    }

    @Test
    void getCustomers() {
        //given
        //when
        controllerUnderTest.getCustomers();
        //then
        verify(customerService).getCustomers();
    }

    @Test
    void getCredits() {
        //given
        //when
        controllerUnderTest.getCredits();
        //then
        verify(customerService).getCredits();
    }

    @Test
    void createCredit() {
        //given
        Credit credit = new Credit("Home", 750000);
        Customer customer = new Customer("John", "Galt", "99999999999");
        Borrower borrower = new Borrower(customer, credit);
        //when
        controllerUnderTest.createCredit(borrower);
        //then
        verify(customerService).createCredit(borrower);
    }

    @Test
    void searchCustomer() {
        //given
        String pesel = "99999999999";
        //when
        controllerUnderTest.searchCustomer(pesel);
        //then
        verify(customerService).searchCustomer(pesel);
    }

    @Test
    void findCustomersById() {
        //given
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        //when
        controllerUnderTest.findCustomersById(ids);
        //then
        verify(customerService).searchCustomer(ids);
    }

    @Test
    void createCustomer() {
        //given
        Customer customer = new Customer("John", "Galt", "99999999999");
        //when
        controllerUnderTest.createCustomer(customer);
        //then
        verify(customerService).createConsumer(customer);
    }
}