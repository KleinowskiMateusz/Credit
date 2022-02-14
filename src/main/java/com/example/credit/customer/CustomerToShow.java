package com.example.credit.customer;

import com.example.credit.abstractt.ToShow;

public class CustomerToShow extends ToShow {

    private final String firstName;
    private final String lastName;
    private final String pesel;

    public CustomerToShow(Customer customer) {
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.pesel = customer.getPesel();
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPesel() {
        return pesel;
    }
}
