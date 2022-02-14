package com.example.credit.customer;


import com.example.credit.credit.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


    // moje
    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(){
        System.out.print(ResponseEntity.ok(customerService.getCustomers()));
        return ResponseEntity.ok(customerService.getCustomers());
    }


    //@JsonView(View.Summary.class)
    @GetMapping("getCredits")
    public ResponseEntity<List<Credit>> getCredits(){ return customerService.getCredits();}

    @PostMapping("/createCredit")
    public Long createCredit(@RequestBody Borrower borrower){
       return customerService.createCredit(borrower);
    }
}
