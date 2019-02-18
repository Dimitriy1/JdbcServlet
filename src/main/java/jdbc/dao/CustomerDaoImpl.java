package jdbc.dao;

import jdbc.MyException;
import jdbc.model.Customer;
import jdbc.model.Project;
import jdbc.model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

public class CustomerDaoImpl extends AbstractDao implements CustomerDao {
    public CustomerDaoImpl(Connection connection) {
        super(connection);
    }

    public Set<Customer> findAllCustomers() {
        final String findAllCustomers = "SELECT * FROM customer";
        final String findAllProjectsForConcreteCustomer = "SELECT * FROM project WHERE customer_id = ?";
        Set<Customer> customers = new LinkedHashSet<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(findAllCustomers);
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setAge(rs.getInt("age"));
                customer.setName(rs.getString("name"));
                customer.setSex(rs.getString("sex"));
                customers.add(customer);
            }

            for (Customer customer : customers) {
                PreparedStatement preparedStatement = connection.prepareStatement(findAllProjectsForConcreteCustomer);
                preparedStatement.setInt(1, customer.getId());
                rs = preparedStatement.executeQuery();
                Project project;
                while (rs.next()) {
                    project = new Project();
                    project.setId(rs.getInt("id"));
                    project.setName(rs.getString("name"));
                    project.setCost(rs.getDouble("cost"));
                    project.setTypeOfProject(rs.getString("type"));
                    customer.addProject(project);
                }
            }

            return customers;
        } catch (SQLException e) {
            throw new MyException(e,"something went wrong");
        }
    }

    public void updateCustomer(Customer customer) {
        final String updateCustomer = "UPDATE customer SET name = ?, age = ?, sex = ? WHERE id = ?";
        final String deleteProjects = "DELETE FROM project WHERE customer_id = ?";
        final String insertNewProjects = "INSERT INTO project (customer_id, name, type, cost) values (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(updateCustomer);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setInt(2, customer.getAge());
            preparedStatement.setString(3, customer.getSex());
            preparedStatement.setInt(4, customer.getId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(deleteProjects);
            preparedStatement.setInt(1,customer.getId());
            preparedStatement.executeUpdate();

            Set<Project> projects = customer.getProjects();
            for (Project project : projects) {
                preparedStatement = connection.prepareStatement(insertNewProjects);
                preparedStatement.setInt(1, customer.getId());
                preparedStatement.setString(2, project.getName());
                preparedStatement.setString(3, project.getTypeOfProject());
                preparedStatement.setDouble(4, project.getCost());
                preparedStatement.executeUpdate();
            }
            customer.setId(findMaxId(Table.CUSTOMER));
        } catch (SQLException e) {
            throw new MyException(e,"something went wrong");
        }
    }

    public Customer findCustomerById(Integer id) {
        final String getAllCustomers = "SELECT * FROM customer WHERE id = " + id;
        final String getAllProjects = "SELECT * FROM project WHERE customer_id = " + id;
        Customer customer = new Customer();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(getAllCustomers);
            if (rs.next()) {
                customer.setId(rs.getInt("id"));
                customer.setAge(rs.getInt("age"));
                customer.setName(rs.getString("name"));
                customer.setSex(rs.getString("sex"));
            }

            statement = connection.createStatement();
            rs = statement.executeQuery(getAllProjects);
            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setTypeOfProject(rs.getString("name"));
                project.setCost(rs.getDouble("cost"));
                customer.addProject(project);
            }

            return customer;
        } catch (SQLException e) {
            throw new MyException(e,"something went wrong");
        }
    }

    public void deleteCustomerById(Integer id) {
        final String updateProjects = "DELETE FROM project WHERE customer_id = ?";
        final String deleteCustomer = "DELETE FROM customer WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteCustomer);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(updateProjects);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new MyException(e,"something went wrong");
        }
    }

    public void insertCustomer(Customer customer) {
        final String insertCustomer = "INSERT INTO customer(name, age, sex) values (?, ?, ?)";
        final String addProject = "INSERT INTO project(customer_id, name, type, cost) values (?, ?, ?, ?)";
        Integer maxId = null;

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(insertCustomer);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setInt(2, customer.getAge());
            preparedStatement.setString(3, customer.getSex());
            preparedStatement.executeUpdate();

            maxId = findMaxId(Table.CUSTOMER);
            customer.setId(maxId);

            Set<Project> projects = customer.getProjects();
            for (Project project : projects) {
                preparedStatement = connection
                        .prepareStatement(addProject);
                preparedStatement.setInt(1, maxId);
                preparedStatement.setString(2, project.getName());
                preparedStatement.setString(3, project.getTypeOfProject());
                preparedStatement.setDouble(4, project.getCost());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new MyException(e,"something went wrong");
        }
    }
}
