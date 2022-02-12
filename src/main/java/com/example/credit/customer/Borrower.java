package com.example.credit.customer;

import com.example.credit.customer.Customer;

public class Borrower extends Customer {
    private String creditName;
    private double value;

    public Borrower(String firstName, String lastName, String pesel, String creditName, double value) {
        super(firstName, lastName, pesel);
        this.creditName = creditName;
        this.value = value;
    }

    public String getCreditName() {
        return creditName;
    }
    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Borrower{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", pesel='" + getPesel() + '\'' +
                ", creditName='" + creditName + '\'' +
                ", value=" + value +
                '}';
    }
}
