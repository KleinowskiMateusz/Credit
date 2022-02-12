package com.example.credit.customer;

import com.example.credit.credit.Credit;
import com.example.credit.credit.CreditRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Resource
    private final CustomerRepository customerRepository;

    @Resource
    private final CreditRepository creditRepository;

    public CustomerService(CustomerRepository customerRepository, CreditRepository creditRepository) {
        this.customerRepository = customerRepository;
        this.creditRepository = creditRepository;
    }

    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    public void getCredits(){
        List<Credit> credits = creditRepository.findAll();
        for (int i=0; i<credits.size(); i++){
            System.out.print(credits.get(i).getCustomer());
        }
    }


    public Long createCredit(Borrower borrower){
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByPesel(borrower.getPesel());
        if ( ! optionalCustomer.isPresent() ){
            Customer newCustomer = new Customer(borrower.getFirstName(), borrower.getLastName(), borrower.getPesel());
            addCustomer(newCustomer);
            Credit newCredit = new Credit(borrower.getCreditName(), borrower.getValue(), newCustomer.getId());
            creditRepository.save(newCredit);
            return newCredit.getCreditID();
        }
        else if ( isCorrectData(borrower, optionalCustomer.get())) {
            Credit newCredit = new Credit(borrower.getCreditName(), borrower.getValue(), optionalCustomer.get().getId());
            creditRepository.save(newCredit);
            return newCredit.getCreditID();
        }
        else return null;
    }

    public boolean isCorrectData(Borrower borrower, Customer customer){
        if ( !borrower.getFirstName().equals(customer.getFirstName())) return false;
        if ( !borrower.getLastName().equals(customer.getLastName())) return false;
        if ( !borrower.getPesel().equals(customer.getPesel())) return false;
        return true;
    }



    public void addCustomer(Customer customer){
        customerRepository.save(customer);
    }
}
