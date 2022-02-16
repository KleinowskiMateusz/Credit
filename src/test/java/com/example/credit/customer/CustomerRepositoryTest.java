package com.example.credit.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repositoryUnderTest;

    @AfterEach
    void clearTestDataBase(){
        repositoryUnderTest.deleteAll();
    }

    @Test
    void findCustomerByCorrectPesel() {
        //given
        Customer customer = new Customer("John", "Galt", "99999999996");
        repositoryUnderTest.save(customer);

        //when
        Optional<Customer> expected = repositoryUnderTest.findCustomerByPesel("99999999996");

        //then
        assertThat(expected.isPresent()).isTrue();
        assertThat(expected.get().getId()).isEqualTo(customer.getId());
        assertThat(expected.get().getPesel()).isEqualTo(customer.getPesel());
        assertThat(expected.get().getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(expected.get().getLastName()).isEqualTo(customer.getLastName());
    }

    @Test
    void findCustomerByFakePesel() {
        //given
        //when
        Optional<Customer> expected = repositoryUnderTest.findCustomerByPesel("99999999990");

        //then
        assertThat(expected.isPresent()).isFalse();
    }
}