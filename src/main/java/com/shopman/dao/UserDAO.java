package com.shopman.dao;

import com.shopman.model.User;
import com.shopman.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public User authenticate(String username, String password, String role) throws SQLException {
        String sql;
        if (role == null || role.trim().isEmpty()) {
            sql = "SELECT u.id, u.username, u.role, u.name, u.email " +
                  "FROM user u " +
                  "WHERE u.username = ? AND u.password = ?";
        } else {
            sql = "SELECT u.id, u.username, u.role, u.name, u.email " +
                  "FROM user u " +
                  "WHERE u.username = ? AND u.password = ? AND u.role = ?";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            if (role != null && !role.trim().isEmpty()) {
                stmt.setString(3, role);
            }

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                return user;
            }
        }

        return null;
    }

    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT u.id, u.username, u.role, u.name, u.email " +
                    "FROM user u " +
                    "WHERE u.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                return user;
            }
        }

        return null;
    }

    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT u.id, u.username, u.role, u.name, u.email " +
                    "FROM user u " +
                    "WHERE u.username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                return user;
            }
        }

        return null;
    }
}

