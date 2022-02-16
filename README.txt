# Credit
1) Ścieżka do pliku tworzącego bazę danych --> Credit/creditDatabaseCreator.sql
2) Przykładowe wywołania usług:
    -- GET --> http://localhost:8080/customer --> wszyscy kredytobiorcy wraz z informacjami
    -- GET --> http://localhost:8080/customer/getCredits --> wszystkie kredyty, wraz z kredytobiorcami (zgodnie ze specyfikacją)
    -- POST --> http://localhost:8080/customer/createCredit (BODY: {"Customer": {"firstName": "John", "lastName": "Galt", "pesel": "44444444444"}, "Credit": {"creditName": "Home","value": 450123.54}}) --> id otrzymanego kredytu
    -- POST --> http://localhost:8080/customer/search (BODY: 44444444444) --> dane kredytobiorcy
    -- POST --> http://localhost:8080/customer/filter (BODY: [2,5,7] --> dane kredytobiorców
    -- POST --> http://localhost:8080/customer/create (BODY: {"firstName": "John", "lastName": "Galt", "pesel": "33333333333"}) --> id dodanego kredytobiorcy
