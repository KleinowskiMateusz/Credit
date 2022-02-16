package com.example.credit.customer;

import com.example.credit.credit.Credit;
import com.fasterxml.jackson.annotation.JsonProperty;

// wrapper dla kredytobiorcy i jego kredytu
public class Borrower {

    @JsonProperty("Customer")
    private Customer customer;

    @JsonProperty("Credit")
    private Credit credit;

    public Borrower(Customer customer, Credit credit) {
        this.customer = customer;
        this.credit = credit;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Credit getCredit() {
        return credit;
    }
    public void setCredit(Credit credit) {
        this.credit = credit;
    }
}
