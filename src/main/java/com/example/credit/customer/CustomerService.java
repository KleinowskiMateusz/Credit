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

    // dane klientów i kredytów są już połączone w klasie klienta (relacja 1-wiele)
    // tworzonych jest wiele nowych obiektów ze względu na wymaganie ukrycia id
    // gdyby można było pokazać wszystkie pola encji nie trzebaby tworzyć klas toShow
    // ukrycie przez @JsonIgnore nic nie da, bo te pola są potrzebne w innych miejscach w programie
    // aby odpowiedz byla bardziej klarowna można zwracać klienta i wszystkie jego kredyty w postaci listy (redukcja nadmiarowych obiektow)
    // tym bardziej, że w klasie Customer są referencje do wszytskich kredytów, nie trzebaby było tworzyć nowych obiektów
    public ResponseEntity<List<HashMap<String, ToShow>>> getCredits(){
        List<Credit> credits = creditRepository.findAll();
        List<HashMap<String, ToShow>> toReturn = new ArrayList<>();
        HashMap<String, ToShow> currentCredit;
        for (int i=0; i<credits.size(); i++){
            currentCredit = new HashMap<>();
            currentCredit.put("Customer", new CustomerToShow((credits.get(i).getCustomer())));
            currentCredit.put("Credit", new CreditToShow((credits.get(i))));
            toReturn.add(currentCredit);
        }
        ResponseEntity<List<HashMap<String, ToShow>>> response = ResponseEntity.ok(toReturn);
        return response;
    }

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

    public boolean isCorrectData(Customer customer, Customer optionalCustomer){
        if ( !customer.getFirstName().equals(optionalCustomer.getFirstName())) return false;
        if ( !customer.getLastName().equals(optionalCustomer.getLastName())) return false;
        if ( !customer.getPesel().equals(optionalCustomer.getPesel())) return false;
        return true;
    }

    public ResponseEntity<Customer> findCustomerByPesel(String pesel) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByPesel(pesel);
        if ( optionalCustomer.isPresent() ) return ResponseEntity.ok(optionalCustomer.get());
        else return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Long> createConsumer(Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByPesel(customer.getPesel());
        if ( optionalCustomer.isPresent() ) return ResponseEntity.badRequest().build();
        else {
            customerRepository.save(customer);
            return ResponseEntity.ok(customer.getId());
        }
    }

    public ResponseEntity<List<Customer>> findCustomersById(List<Long> ids) {
        ArrayList<Customer> customers = new ArrayList<>();
        Optional<Customer> optionalCustomer;
        System.out.print(ids);
        for (int i=0; i<ids.size(); i++){
            optionalCustomer = customerRepository.findById(ids.get(i));
            if ( optionalCustomer.isPresent() ) {
                customers.add(optionalCustomer.get());
            }
        }
        return ResponseEntity.ok(customers);
    }
}
