package com.bookstore.dao;

import com.bookstore.model.Customer;
import com.bookstore.model.User;
import com.bookstore.model.Address;
import com.bookstore.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    
    private static final String INSERT_CUSTOMER_SQL = 
        "INSERT INTO customers (user_id, phone, date_of_birth, address_id) VALUES (?, ?, ?, ?)";
    
    private static final String SELECT_CUSTOMER_BY_ID = 
        "SELECT c.id, c.user_id, c.phone, c.date_of_birth, c.address_id, c.created_at, " +
        "u.username, u.email, u.first_name, u.last_name, u.created_at as user_created_at, u.updated_at as user_updated_at, " +
        "a.street, a.city, a.state, a.postal_code, a.country " +
        "FROM customers c " +
        "LEFT JOIN users u ON c.user_id = u.id " +
        "LEFT JOIN addresses a ON c.address_id = a.id " +
        "WHERE c.id = ?";
    
    private static final String SELECT_CUSTOMER_BY_USER_ID = 
        "SELECT c.id, c.user_id, c.phone, c.date_of_birth, c.address_id, c.created_at " +
        "FROM customers c WHERE c.user_id = ?";
    
    private static final String SELECT_ALL_CUSTOMERS = 
        "SELECT c.id, c.user_id, c.phone, c.date_of_birth, c.address_id, c.created_at, " +
        "u.username, u.email, u.first_name, u.last_name " +
        "FROM customers c " +
        "LEFT JOIN users u ON c.user_id = u.id " +
        "ORDER BY c.created_at DESC";
    
    private static final String UPDATE_CUSTOMER_SQL = 
        "UPDATE customers SET phone = ?, date_of_birth = ?, address_id = ? WHERE id = ?";
    
    private static final String DELETE_CUSTOMER_SQL = "DELETE FROM customers WHERE id = ?";
    
    // Create customer
    public boolean insertCustomer(Customer customer) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            
            preparedStatement.setInt(1, customer.getUserId());
            preparedStatement.setString(2, customer.getPhone());
            preparedStatement.setDate(3, customer.getDateOfBirth());
            if (customer.getAddressId() > 0) {
                preparedStatement.setInt(4, customer.getAddressId());
            } else {
                preparedStatement.setNull(4, Types.INTEGER);
            }
            
            int result = preparedStatement.executeUpdate();
            
            if (result > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    customer.setId(generatedKeys.getInt(1));
                }
                return true;
            }
            return false;
        }
    }
    
    // Read customer by ID with related objects
    public Customer selectCustomer(int id) {
        Customer customer = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMER_BY_ID)) {
            
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                customer = mapResultSetToCustomerWithRelations(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
    
    // Read customer by user ID
    public Customer selectCustomerByUserId(int userId) {
        Customer customer = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMER_BY_USER_ID)) {
            
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                customer = mapResultSetToCustomer(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
    
    // Read all customers
    public List<Customer> selectAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CUSTOMERS)) {
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                customers.add(mapResultSetToCustomerWithUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    
    // Update customer
    public boolean updateCustomer(Customer customer) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER_SQL)) {
            
            statement.setString(1, customer.getPhone());
            statement.setDate(2, customer.getDateOfBirth());
            if (customer.getAddressId() > 0) {
                statement.setInt(3, customer.getAddressId());
            } else {
                statement.setNull(3, Types.INTEGER);
            }
            statement.setInt(4, customer.getId());
            
            return statement.executeUpdate() > 0;
        }
    }
    
    // Delete customer
    public boolean deleteCustomer(int id) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER_SQL)) {
            
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }
    
    // Utility methods
    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setUserId(rs.getInt("user_id"));
        customer.setPhone(rs.getString("phone"));
        customer.setDateOfBirth(rs.getDate("date_of_birth"));
        customer.setAddressId(rs.getInt("address_id"));
        customer.setCreatedAt(rs.getTimestamp("created_at"));
        return customer;
    }
    
    private Customer mapResultSetToCustomerWithUser(ResultSet rs) throws SQLException {
        Customer customer = mapResultSetToCustomer(rs);
        
        // Map user information
        User user = new User();
        user.setId(customer.getUserId());
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        
        customer.setUser(user);
        return customer;
    }
    
    private Customer mapResultSetToCustomerWithRelations(ResultSet rs) throws SQLException {
        Customer customer = mapResultSetToCustomer(rs);
        
        // Map user information
        User user = new User();
        user.setId(customer.getUserId());
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setCreatedAt(rs.getTimestamp("user_created_at"));
        user.setUpdatedAt(rs.getTimestamp("user_updated_at"));
        customer.setUser(user);
        
        // Map address information
        if (rs.getString("street") != null) {
            Address address = new Address();
            address.setId(customer.getAddressId());
            address.setStreet(rs.getString("street"));
            address.setCity(rs.getString("city"));
            address.setState(rs.getString("state"));
            address.setPostalCode(rs.getString("postal_code"));
            address.setCountry(rs.getString("country"));
            customer.setAddress(address);
        }
        
        return customer;
    }
} 