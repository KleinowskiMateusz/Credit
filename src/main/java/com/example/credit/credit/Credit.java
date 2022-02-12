package com.example.credit.credit;

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
    private int customerID;

    public Credit(Long creditID, String creditName, double value, int customerID) {
        this.creditID = creditID;
        this.creditName = creditName;
        this.value = value;
        this.customerID = customerID;
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

    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
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
