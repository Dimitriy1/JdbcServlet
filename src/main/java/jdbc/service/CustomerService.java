package jdbc.service;

import jdbc.model.Customer;
import java.util.Set;

public interface CustomerService {
    Set<Customer> findAllCustomers();

    void updateCustomerById(Integer id);

    void deleteCustomer(Integer id);

    Customer findCustomerById(Integer id);

    void insertCustomer(Customer customer);
}
