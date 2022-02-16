package com.example.credit.customer;

import com.example.credit.abstractt.ToShow;
import com.example.credit.credit.Credit;
import com.example.credit.credit.CreditRepository;
import com.example.credit.credit.CreditToShow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

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
    void createCreditsWithExistingCustomer() {
        //given
        Credit credit = new Credit("Home", 750000);
        Customer customer = new Customer(1L,"John", "Galt", "99999999999");
        ArgumentCaptor<Credit> creditArgumentCaptor = ArgumentCaptor.forClass(Credit.class);
        Borrower borrower = new Borrower(customer, credit);

        given(customerRepository.findCustomerByPesel(borrower.getCustomer().getPesel())).willReturn(Optional.of(customer));

        //when
        serviceUnderTest.createCredit(borrower);

        //then
        verify(creditRepository).save(creditArgumentCaptor.capture());
        verify(customerRepository, never()).save(any());

        Credit capaturedCredit = creditArgumentCaptor.getValue();
        assertThat(capaturedCredit).isEqualTo(credit);
    }

    @Test
    void createCreditsWithWrongCustomerData() {
        //given
        Credit credit = new Credit("Home", 750000);
        Customer customer = new Customer(1L,"John", "Galt", "99999999999");
        Customer wrongCustomer = new Customer(1L,"Anna", "Smith", "1111111111");
        ArgumentCaptor<Credit> creditArgumentCaptor = ArgumentCaptor.forClass(Credit.class);
        Borrower borrower = new Borrower(customer, credit);

        given(customerRepository.findCustomerByPesel(borrower.getCustomer().getPesel())).willReturn(Optional.of(wrongCustomer));

        //when
        serviceUnderTest.createCredit(borrower);

        //then
        verify(creditRepository, never()).save(any());
        verify(customerRepository, never()).save(any());
    }

    @Test
    void isCorrectDataName(){
        //given
        Customer customer1 = new Customer(1L,"John", "Galt", "99999999999");
        Customer customer2 = new Customer(1L,"Anna", "Galt", "99999999999");
        Customer customer3 = new Customer(1L,"John", "Galt", "99999999999");

        //when
        boolean different = serviceUnderTest.isCorrectData(customer1, customer2);
        boolean same = serviceUnderTest.isCorrectData(customer1, customer3);

        //then
        assertThat(different).isFalse();
        assertThat(same).isTrue();
    }

    @Test
    void isCorrectDataSurname(){
        //given
        Customer customer1 = new Customer(1L,"John", "Galt", "99999999999");
        Customer customer2 = new Customer(1L,"John", "Smith", "99999999999");
        Customer customer3 = new Customer(1L,"John", "Galt", "99999999999");

        //when
        boolean different = serviceUnderTest.isCorrectData(customer1, customer2);
        boolean same = serviceUnderTest.isCorrectData(customer1, customer3);

        //then
        assertThat(different).isFalse();
        assertThat(same).isTrue();
    }

    @Test
    void isCorrectDataPesel(){
        //given
        Customer customer1 = new Customer(1L,"John", "Galt", "99999999999");
        Customer customer2 = new Customer(1L,"John", "Galt", "11111111111");
        Customer customer3 = new Customer(1L,"John", "Galt", "99999999999");

        //when
        boolean different = serviceUnderTest.isCorrectData(customer1, customer2);
        boolean same = serviceUnderTest.isCorrectData(customer1, customer3);

        //then
        assertThat(different).isFalse();
        assertThat(same).isTrue();
    }

    @Test
    void getCredits(){
        //given
        Credit credit1 = new Credit("Home", 750000,1L);
        Credit credit2 = new Credit("Car", 750000,1L);
        Customer customer = new Customer(1L,"John", "Galt", "99999999999");
        List<Credit> credits = Arrays.asList(credit1, credit2);


        given(creditRepository.findAll()).willReturn(credits);
        given(customerRepository.findById(1L)).willReturn(Optional.of(customer));
        //when
        ResponseEntity<List<HashMap<String, ToShow>>> result = serviceUnderTest.getCredits();
        //then
        verify(creditRepository).findAll();

        List<HashMap<String, ToShow>> toReturn = new ArrayList<>();
        HashMap<String, ToShow> creditOne = new HashMap<>();
        HashMap<String, ToShow> creditTwo = new HashMap<>();
        creditOne.put("Customer", new CustomerToShow(customer));
        creditOne.put("Credit", new CreditToShow(credit1));
        creditTwo.put("Customer", new CustomerToShow(customer));
        creditTwo.put("Credit", new CreditToShow(credit2));
        toReturn.add(creditOne);
        toReturn.add(creditTwo);
        ResponseEntity<List<HashMap<String, ToShow>>> response = ResponseEntity.ok(toReturn);

        assertThat(result.getBody().size()).isEqualTo(toReturn.size());
        assertThat(result.getBody().get(0).get("Customer")).isEqualToComparingFieldByField(toReturn.get(0).get("Customer"));
        assertThat(result.getBody().get(1).get("Credit")).isEqualToComparingFieldByField(toReturn.get(1).get("Credit"));
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