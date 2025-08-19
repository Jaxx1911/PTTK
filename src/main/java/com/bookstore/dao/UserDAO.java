package com.bookstore.dao;

import com.bookstore.model.User;
import com.bookstore.util.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    
    private static final String INSERT_USER_SQL = 
        "INSERT INTO users (username, email, password, first_name, last_name) VALUES (?, ?, ?, ?, ?)";
    
    private static final String SELECT_USER_BY_ID = 
        "SELECT id, username, email, password, first_name, last_name, created_at, updated_at FROM users WHERE id = ?";
    
    private static final String SELECT_USER_BY_USERNAME = 
        "SELECT id, username, email, password, first_name, last_name, created_at, updated_at FROM users WHERE username = ?";
    
    private static final String SELECT_USER_BY_EMAIL = 
        "SELECT id, username, email, password, first_name, last_name, created_at, updated_at FROM users WHERE email = ?";
    
    private static final String SELECT_ALL_USERS = 
        "SELECT id, username, email, password, first_name, last_name, created_at, updated_at FROM users";
    
    private static final String DELETE_USER_SQL = "DELETE FROM users WHERE id = ?";
    
    private static final String UPDATE_USER_SQL = 
        "UPDATE users SET username = ?, email = ?, first_name = ?, last_name = ? WHERE id = ?";
    
    private static final String UPDATE_PASSWORD_SQL = 
        "UPDATE users SET password = ? WHERE id = ?";
    
    private static final String CHECK_USERNAME_EXISTS = 
        "SELECT COUNT(*) FROM users WHERE username = ?";
    
    private static final String CHECK_EMAIL_EXISTS = 
        "SELECT COUNT(*) FROM users WHERE email = ?";
    
    // Create user
    public boolean insertUser(User user) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, hashPassword(user.getPassword()));
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            
            int result = preparedStatement.executeUpdate();
            
            if (result > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
                return true;
            }
            return false;
        }
    }
    
    // Read user by ID
    public User selectUser(int id) {
        User user = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                user = mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    // Read user by username
    public User selectUserByUsername(String username) {
        User user = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME)) {
            
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                user = mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    // Read user by email
    public User selectUserByEmail(String email) {
        User user = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                user = mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    // Read all users
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    // Update user
    public boolean updateUser(User user) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_SQL)) {
            
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setInt(5, user.getId());
            
            return statement.executeUpdate() > 0;
        }
    }
    
    // Update password
    public boolean updatePassword(int userId, String newPassword) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PASSWORD_SQL)) {
            
            statement.setString(1, hashPassword(newPassword));
            statement.setInt(2, userId);
            
            return statement.executeUpdate() > 0;
        }
    }
    
    // Delete user
    public boolean deleteUser(int id) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_SQL)) {
            
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }
    
    // Validate user login
    public User validateUser(String usernameOrEmail, String password) {
        User user = null;
        
        // Try to find by username first, then by email
        user = selectUserByUsername(usernameOrEmail);
        if (user == null) {
            user = selectUserByEmail(usernameOrEmail);
        }
        
        if (user != null && verifyPassword(password, user.getPassword())) {
            return user;
        }
        
        return null;
    }
    
    // Check if username exists
    public boolean usernameExists(String username) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_USERNAME_EXISTS)) {
            
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Check if email exists
    public boolean emailExists(String email) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_EMAIL_EXISTS)) {
            
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Utility methods
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));
        return user;
    }
    
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    private boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
} 