package com.example.credit.customer;


import com.example.credit.abstractt.ToShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(){
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("getCredits")
    public ResponseEntity<List<HashMap<String, ToShow>>> getCredits(){
        return customerService.getCredits();
    }

    @PostMapping("/createCredit")
    public ResponseEntity<Long> createCredit(@RequestBody Borrower borrower){
       return customerService.createCredit(borrower);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomer(@RequestBody String pesel){
        return customerService.searchCustomer(pesel);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<Customer>> findCustomersById(@RequestBody List<Long> ids){
        return customerService.searchCustomer(ids);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createCustomer(@RequestBody Customer customer){
        return customerService.createConsumer(customer);
    }
}