package jdbc.dao;

import jdbc.model.Customer;
import java.util.Set;

public interface CustomerDao {
    void updateCustomer(Customer customer);

    Set<Customer> findAllCustomers();

    Customer findCustomerById(Integer id);

    void deleteCustomerById(Integer id);

    void insertCustomer(Customer customer);
}
