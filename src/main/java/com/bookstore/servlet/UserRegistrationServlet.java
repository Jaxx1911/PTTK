package com.bookstore.servlet;

import com.bookstore.dao.UserDAO;
import com.bookstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class UserRegistrationServlet extends HttpServlet {
    private UserDAO userDAO;
    
    @Override
    public void init() {
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form parameters
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        
        // Validation
        String errorMessage = validateRegistrationData(username, email, password, confirmPassword, firstName, lastName);
        
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        
        // Check if username or email already exists
        if (userDAO.usernameExists(username)) {
            request.setAttribute("errorMessage", "Username already exists. Please choose a different username.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        
        if (userDAO.emailExists(email)) {
            request.setAttribute("errorMessage", "Email already exists. Please use a different email address.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        
        // Create new user
        User user = new User(username, email, password, firstName, lastName);
        
        try {
            boolean isUserCreated = userDAO.insertUser(user);
            
            if (isUserCreated) {
                request.setAttribute("successMessage", "Registration successful! Please log in with your credentials.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error occurred. Please try again later.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
    
    private String validateRegistrationData(String username, String email, String password, 
                                          String confirmPassword, String firstName, String lastName) {
        
        // Check for empty fields
        if (username == null || username.trim().isEmpty()) {
            return "Username is required.";
        }
        if (email == null || email.trim().isEmpty()) {
            return "Email is required.";
        }
        if (password == null || password.trim().isEmpty()) {
            return "Password is required.";
        }
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            return "Please confirm your password.";
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            return "First name is required.";
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            return "Last name is required.";
        }
        
        // Username validation
        if (username.length() < 3 || username.length() > 50) {
            return "Username must be between 3 and 50 characters.";
        }
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            return "Username can only contain letters, numbers, and underscores.";
        }
        
        // Email validation
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            return "Please enter a valid email address.";
        }
        
        // Password validation
        if (password.length() < 6) {
            return "Password must be at least 6 characters long.";
        }
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match.";
        }
        
        // Name validation
        if (firstName.length() > 50) {
            return "First name must not exceed 50 characters.";
        }
        if (lastName.length() > 50) {
            return "Last name must not exceed 50 characters.";
        }
        
        return null; // No validation errors
    }
} 