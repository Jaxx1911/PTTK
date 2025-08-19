package com.bookstore.servlet;

import com.bookstore.dao.UserDAO;
import com.bookstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {
    private UserDAO userDAO;
    
    @Override
    public void init() {
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String usernameOrEmail = request.getParameter("usernameOrEmail");
        String password = request.getParameter("password");
        
        // Validation
        if (usernameOrEmail == null || usernameOrEmail.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Username or email is required.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Password is required.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        // Validate user credentials
        User user = userDAO.validateUser(usernameOrEmail.trim(), password);
        
        if (user != null) {
            // Login successful
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userId", user.getId());
            
            // Redirect to dashboard or previous page
            String redirectURL = request.getParameter("redirect");
            if (redirectURL != null && !redirectURL.isEmpty()) {
                response.sendRedirect(redirectURL);
            } else {
                response.sendRedirect("dashboard");
            }
        } else {
            // Login failed
            request.setAttribute("errorMessage", "Invalid username/email or password. Please try again.");
            request.setAttribute("usernameOrEmail", usernameOrEmail); // Preserve input
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
} 