package com.example.credit.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(1L, "John", "Galt", "11111111111");
    }

    @Test
    void getId() {
        //given
        Long expected = 1L;
        //when
        Long id = customer.getId();
        //then
        assertThat(id).isEqualTo(expected);
    }

    @Test
    void setId() {
        //given
        Long expected = 3L;
        //when
        customer.setId(expected);
        Long id = customer.getId();
        //then
        assertThat(id).isEqualTo(expected);
    }

    @Test
    void getFirstName() {
        //given
        String expected = "John";
        //when
        String name = customer.getFirstName();
        //then
        assertThat(name).isEqualTo(expected);
    }

    @Test
    void setFirstName() {
        //given
        String expected = "Anna";
        //when
        customer.setFirstName(expected);
        String name = customer.getFirstName();
        //then
        assertThat(name).isEqualTo(expected);
    }

    @Test
    void getLastName() {
        //given
        String expected = "Galt";
        //when
        String name = customer.getLastName();
        //then
        assertThat(name).isEqualTo(expected);
    }

    @Test
    void setLastName() {
        //given
        String expected = "Smith";
        //when
        customer.setLastName(expected);
        String name = customer.getLastName();
        //then
        assertThat(name).isEqualTo(expected);
    }

    @Test
    void getPesel() {
        //given
        String expected = "11111111111";
        //when
        String pesel = customer.getPesel();
        //then
        assertThat(pesel).isEqualTo(expected);
    }

    @Test
    void setPesel() {
        //given
        String expected = "77777777777";
        //when
        customer.setPesel(expected);
        String name = customer.getPesel();
        //then
        assertThat(name).isEqualTo(expected);
    }
}