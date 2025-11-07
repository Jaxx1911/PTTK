package com.shopman.dao;

import com.shopman.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class DAO {
    protected Connection conn;

    public DAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }
}
