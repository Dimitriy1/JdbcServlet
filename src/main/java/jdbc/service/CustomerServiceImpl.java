package jdbc.service;

import jdbc.dao.CustomerDao;
import jdbc.model.Customer;
import java.util.Set;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Set<Customer> findAllCustomers() { return this.customerDao.findAllCustomers(); }

    public void updateCustomerById(Integer id) {
        customerDao.findCustomerById(id);
    }

    public void deleteCustomer(Integer id) {
        customerDao.deleteCustomerById(id);
    }

    public Customer findCustomerById(Integer id) {
        return customerDao.findCustomerById(id);
    }

    public void insertCustomer(Customer customer) {
        customerDao.insertCustomer(customer);
    }
}
