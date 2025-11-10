package com.shopman.servlet;

import com.shopman.dao.UserDAO;
import com.shopman.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/Login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin!");
            request.getRequestDispatcher("/Login.jsp").forward(request, response);
            return;
        }

        try {
            User user = userDAO.authenticate(username, password, null);

            if (user != null) {
                // Đăng nhập thành công
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("userName", user.getName());
                session.setAttribute("userRole", user.getRole());

                // Chuyển hướng dựa trên role
                if ("manager".equalsIgnoreCase(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/manager/HomeManager.jsp");
                } else if ("customer".equalsIgnoreCase(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/customer/HomeCustomer.jsp");
                } else {
                    request.setAttribute("error", "Vai trò người dùng không hợp lệ!");
                    request.getRequestDispatcher("/Login.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("/Login.jsp").forward(request, response);
        }
    }
}

