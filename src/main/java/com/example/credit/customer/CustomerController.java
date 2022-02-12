package com.example.credit.customer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping("getCredits")
    public void getCredits(){ customerService.getCredits();}

    @PostMapping("/createCredit")
    public Long createCredit(@RequestBody Borrower borrower){
        return customerService.createCredit(borrower);
    }
}
