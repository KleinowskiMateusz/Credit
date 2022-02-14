package com.example.credit.customer;


import com.example.credit.abstractt.ToShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


    // moje
    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(){
        System.out.print(ResponseEntity.ok(customerService.getCustomers()));
        return ResponseEntity.ok(customerService.getCustomers());
    }


    @GetMapping("getCredits")
    public ResponseEntity<List<HashMap<String, ToShow>>> getCredits(){ return customerService.getCredits();}

/*
{
  "Customer": {
    "firstName": "Ola",
    "lastName": "Ratata",
    "pesel": "110891032"
    },
    "Credit": {
        "creditName": "Goferek",
        "value": 10.7
    }
}
*/
    @PostMapping("/createCredit")
    public ResponseEntity<Long> createCredit(@RequestBody Borrower borrower){
       return customerService.createCredit(borrower);
    }
// 110891032
    @PostMapping("/search")
    public ResponseEntity<Customer> findCustomerByPesel(@RequestBody String pesel){
        return customerService.findCustomerByPesel(pesel);
    }
/*
{
    "firstName": "Olga",
    "lastName": "Ratata",
    "pesel": "110891037"
}
*/
    @PostMapping("/create")
    public ResponseEntity<Long> create(@RequestBody Customer customer){
        return customerService.createConsumer(customer);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<Customer>> findCustomersById(@RequestBody List<Long> ids){
        return customerService.findCustomersById(ids);
    }



}
