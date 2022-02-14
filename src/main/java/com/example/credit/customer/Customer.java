package com.example.credit.customer;

import com.example.credit.credit.Credit;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity(name="Customer")
@Table(
        name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(name="pesel_unique", columnNames = "pesel")
        }
)
public class Customer {

    @Id
    @SequenceGenerator(
            name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "firstname",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            name = "lastname",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
            name = "pesel",
            nullable = false
    )
    private String pesel;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Credit> credits;


    public Customer(Long id, String firstName, String lastName, String pesel) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
    }

    public Customer(String firstName, String lastName, String pesel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPesel() {
        return pesel;
    }
    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("");
        builder.append("Customer{" + "id=" + id +
                        ", firstName='" + firstName + '\'' +
                        ", lastName='" + lastName + '\'' +
                        ", pesel='" + pesel + '\'' +
                        ", creditsID=[");
        if (credits != null) {
            for (int i = 0; i < credits.size(); i++) {
                builder.append(credits.get(i));
                if (i + 1 < credits.size()) {
                    builder.append("\n");
                }
            }
        }
        builder.append("]" + "}");

        return builder.toString();
    }
}
