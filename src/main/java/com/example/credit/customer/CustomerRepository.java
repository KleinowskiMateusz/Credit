package com.example.credit.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c from Customer c WHERE c.pesel = ?1")
    Optional<Customer> findCustomerByPesel(String pesel);
}
