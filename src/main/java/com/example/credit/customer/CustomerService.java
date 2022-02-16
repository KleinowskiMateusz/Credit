package com.example.credit.customer;

import com.example.credit.abstractt.ToShow;
import com.example.credit.credit.Credit;
import com.example.credit.credit.CreditRepository;
import com.example.credit.credit.CreditToShow;
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

    // dane klientów i kredytów mogą być połączone w klasie klienta (relacja 1-wiele)
    // tworzonych jest wiele nowych obiektów ze względu na wymaganie ukrycia id
    // gdyby można było pokazać wszystkie pola encji nie trzebaby było tworzyć klas dziedziczących po toShow
    // ukrycie przez @JsonIgnore nic nie da, bo te pola są potrzebne w innych miejscach w programie
    // aby odpowiedz byla bardziej klarowna można zwracać klienta i wszystkie jego kredyty w postaci listy (redukcja nadmiarowych obiektow)
    public ResponseEntity<List<HashMap<String, ToShow>>> getCredits(){
        List<Credit> credits = creditRepository.findAll();
        Optional<Customer> optionalCustomer;
        List<HashMap<String, ToShow>> toReturn = new ArrayList<>();
        HashMap<String, ToShow> currentCredit;
        for (Credit credit : credits) {
            currentCredit = new HashMap<>();
            optionalCustomer = customerRepository.findById(credit.getCustomerID());
            if (optionalCustomer.isPresent()) {
                currentCredit.put("Customer", new CustomerToShow(optionalCustomer.get()));
                currentCredit.put("Credit", new CreditToShow(credit));
                toReturn.add(currentCredit);
            }
        }
        ResponseEntity<List<HashMap<String, ToShow>>> response = ResponseEntity.ok(toReturn);
        return response;
    }

    // tworzy nowego kredytobiorce, jeżeli jego danych nie ma w bazie ( sprawdzam na podstawie peselu - jest unikalny )
    // jeżeli dany pesel jest w bazie, ale dane się nie zgadzają, zwracam błąd
    public ResponseEntity<Long> createCredit(Borrower borrower){
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByPesel(borrower.getCustomer().getPesel());
        if ( ! optionalCustomer.isPresent() ){
            customerRepository.save(borrower.getCustomer());
            borrower.getCredit().setCustomerID(borrower.getCustomer().getId());
            creditRepository.save(borrower.getCredit());
            return ResponseEntity.ok(borrower.getCredit().getCreditID());
        }
        else if ( isCorrectData(borrower.getCustomer(), optionalCustomer.get())) {
            borrower.getCredit().setCustomerID(optionalCustomer.get().getId());
            creditRepository.save(borrower.getCredit());
            return ResponseEntity.ok(borrower.getCredit().getCreditID());
        }
        else return ResponseEntity.badRequest().build();
    }

    // waliduje dane 2 kredytobiorców
    public boolean isCorrectData(Customer customer, Customer optionalCustomer){
        if ( !customer.getFirstName().equals(optionalCustomer.getFirstName())) return false;
        if ( !customer.getLastName().equals(optionalCustomer.getLastName())) return false;
        if ( !customer.getPesel().equals(optionalCustomer.getPesel())) return false;
        return true;
    }

    public ResponseEntity<List<Customer>> searchCustomer(String pesel) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByPesel(pesel);
        if ( optionalCustomer.isPresent() ) return ResponseEntity.ok(Arrays.asList(optionalCustomer.get()));
        else return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Customer>> searchCustomer(List<Long> ids) {
        List<Customer> foundCustomers = customerRepository.findAllById(ids);
        return ResponseEntity.ok(foundCustomers);
    }

    public ResponseEntity<Long> createConsumer(Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByPesel(customer.getPesel());
        if ( optionalCustomer.isPresent() ) return ResponseEntity.badRequest().build();
        else {
            customerRepository.save(customer);
            return ResponseEntity.ok(customer.getId());
        }
    }
}
