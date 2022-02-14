package com.example.credit.customer;

import com.example.credit.credit.Credit;
import com.example.credit.credit.CreditRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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

    // dane klientów i kredytów są już połączone w klasie klienta (relacja 1-wiele)
    // metoda wykonana zgodnie ze specyfikacją, lecz można to zrobić szybciej pobierając jedynie listę klientów i wykonując na niej odpowiednie operacje, niepotrzebnie tworzone nowe obiekty (HashSet,  instancje wrappera Borrower)
    public ResponseEntity<List<Credit>> getCredits(){
        List<Credit> credits = creditRepository.findAll();
        ResponseEntity<List<Credit>> response = ResponseEntity.ok(credits);
        return response;
    }

//    HashSet<Long> customerIds = new HashSet<>();
//        for (int i=0; i<credits.size(); i++) {
//        customerIds.add(credits.get(i).getCustomerID());
//    }
//    List<Customer> customers = customerRepository.findAllById(customerIds);
//
//    ArrayList<Borrower> toReturn = new ArrayList<>();
//    Customer customer = null;
//
//        for (int i=0; i<credits.size(); i++){
//        for (int j=0; j<customers.size(); j++){
//            if ( customers.get(j).getId() == credits.get(i).getCustomerID()){
//                customer = customers.get(j);
//            }
//        }
//        toReturn.add(new Borrower(customer, credits.get(i)));
//    }
//
//    ResponseEntity<List<Borrower>> response = ResponseEntity.ok(toReturn);

    public Long createCredit(Borrower borrower){
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByPesel(borrower.getCustomer().getPesel());
        if ( ! optionalCustomer.isPresent() ){
            addCustomer(borrower.getCustomer());
            borrower.getCredit().setCustomerID(borrower.getCustomer().getId());
            creditRepository.save(borrower.getCredit());
            return borrower.getCredit().getCreditID();
        }
        else if ( isCorrectData(borrower.getCustomer(), optionalCustomer.get())) {
            borrower.getCredit().setCustomerID(optionalCustomer.get().getId());
            creditRepository.save(borrower.getCredit());
            return borrower.getCredit().getCreditID();
        }
        else return null;
    }

    public boolean isCorrectData(Customer customer, Customer optionalCustomer){
        if ( !customer.getFirstName().equals(optionalCustomer.getFirstName())) return false;
        if ( !customer.getLastName().equals(optionalCustomer.getLastName())) return false;
        if ( !customer.getPesel().equals(optionalCustomer.getPesel())) return false;
        return true;
    }

    public void addCustomer(Customer customer){
        customerRepository.save(customer);
    }
}
