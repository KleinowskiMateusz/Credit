package com.example.credit.customer;

import com.example.credit.credit.Credit;
import com.example.credit.credit.CreditRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CreditRepository creditRepository;
    private CustomerService serviceUnderTest;

    @BeforeEach
    void setUp() {
        serviceUnderTest = new CustomerService(customerRepository, creditRepository);
    }

    @Test
    void getCustomers() {
        //given
        //when
        serviceUnderTest.getCustomers();
        //then
        verify(customerRepository).findAll();
    }

    @Test
    void createCreditsWithNewCustomer() {
        //given
        Credit credit = new Credit("Home", 750000);
        Customer customer = new Customer("John", "Galt", "99999999999");
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        ArgumentCaptor<Credit> creditArgumentCaptor = ArgumentCaptor.forClass(Credit.class);
        Borrower borrower = new Borrower(customer, credit);

        //when
        serviceUnderTest.createCredit(borrower);

        //then
        verify(customerRepository).save(customerArgumentCaptor.capture());
        verify(creditRepository).save(creditArgumentCaptor.capture());

        Customer capaturedCustomer = customerArgumentCaptor.getValue();
        Credit capaturedCredit = creditArgumentCaptor.getValue();

        assertThat(capaturedCustomer).isEqualTo(customer);
        assertThat(capaturedCredit).isEqualTo(credit);
    }

    @Test
    @Disabled
    void createCreditsWithExistingCustomer() {
        //given
        Credit credit = new Credit("Home", 750000);
        Customer customer = new Customer(1L,"John", "Galt", "99999999999");
        ArgumentCaptor<Credit> creditArgumentCaptor = ArgumentCaptor.forClass(Credit.class);
        Borrower borrower = new Borrower(customer, credit);
        Optional<Customer> filled = Optional.of(customer);

        //when
        serviceUnderTest.createCredit(borrower);
        given(customerRepository.findById(customer.getId())).willReturn(filled);

        //then
        verify(creditRepository).save(creditArgumentCaptor.capture());
        verify(customerRepository, never()).save(any());

        Credit capaturedCredit = creditArgumentCaptor.getValue();
        assertThat(capaturedCredit).isEqualTo(credit);
    }

    @Test
    void searchByPesel() {
        //given
        String pesel = "99999999999";
        //when
        serviceUnderTest.searchCustomer(pesel);
        //then
        verify(customerRepository).findCustomerByPesel(pesel);
    }

    @Test
    void searchById() {
        //given
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        //when
        serviceUnderTest.searchCustomer(ids);
        //then
        verify(customerRepository).findAllById(ids);
    }

    @Test
    void createConsumer() {
        //given
        Customer customer = new Customer(1L,"John", "Galt", "99999999999");
        //when
        serviceUnderTest.createConsumer(customer);
        //then
        verify(customerRepository).save(customer);
    }

}