package com.example.credit.credit;

import com.example.credit.customer.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name="Credit")
@Table(name = "credit")
public class Credit {

    @Id
    @SequenceGenerator(
            name = "credit_sequence",
            sequenceName = "credit_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "credit_sequence"
    )
    @Column(
            name = "creditID",
            updatable = false
    )
    private Long creditID;

    @Column(
            name = "creditname",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String creditName;

    @Column(
            name = "value",
            nullable = false
    )
    private double value;

    @Column(
            name = "customerid",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private Long customerID;

    @ManyToOne
    @JoinColumn(name = "customerid", insertable = false, updatable = false)
    @JsonIgnore
    private Customer customer;

    public Credit(Long creditID, String creditName, double value, Long customerID) {
        this.creditID = creditID;
        this.creditName = creditName;
        this.value = value;
        this.customerID = customerID;
    }

    public Credit(String creditName, double value, Long customerID) {
        this.creditName = creditName;
        this.value = value;
        this.customerID = customerID;
    }

    public Credit(String creditName, double value) {
        this.creditName = creditName;
        this.value = value;
    }

    public Credit() {

    }

    public Long getCreditID() {
        return creditID;
    }
    public void setCreditID(Long creditID) {
        this.creditID = creditID;
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

    public Long getCustomerID() {
        return customerID;
    }
    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "creditID=" + creditID +
                ", creditName='" + creditName + '\'' +
                ", value=" + value +
                ", customerID=" + customerID +
                '}';
    }
}
