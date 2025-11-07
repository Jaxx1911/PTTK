package com.shopman.dao;

import com.shopman.model.*;
import com.shopman.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO extends DAO {
    public List<Invoice> getInvoicesByCustomerId(int customerId) throws SQLException {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT i.id, i.date, i.total_amount, i.status " +
                "FROM invoice i " +
                "WHERE i.customer_id = ? " +
                "ORDER BY i.date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Invoice invoice = new Invoice(rs, customerId);

                invoices.add(invoice);
            }
        }

        return invoices;
    }

    public Invoice getInvoiceDetail(int invoiceId) throws SQLException {
        Invoice invoice = new Invoice();
        String sql1 = "SELECT i.id, i.date, i.total_amount, i.status, " +
                "c.id as customer_id, cu.name as customer_name, cu.email, c.phone, " +
                "i.sale_staff_id, ss.name as sale_staff_name, " +
                "i.delivery_staff_id, ds.name as delivery_staff_name " +
                "FROM invoice i " +
                "JOIN customer c ON i.customer_id = c.id " +
                "JOIN user cu ON c.id = cu.id " +
                "LEFT JOIN user ss ON i.sale_staff_id = ss.id AND ss.role = 'sale' " +
                "LEFT JOIN user ds ON i.delivery_staff_id = ds.id AND ds.role = 'delivery' " +
                "WHERE i.id = ?";

        String sql2 = "SELECT ip.quantity, ip.unit_price, ip.sub_total, " +
                "p.id as product_id, p.name, p.description, p.price, p.unit " +
                "FROM invoice_product ip " +
                "JOIN product p ON ip.product_id = p.id " +
                "WHERE ip.invoice_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql1);
             PreparedStatement stmt2 = conn.prepareStatement(sql2);
        ) {

            stmt.setInt(1, invoiceId);
            stmt2.setInt(1, invoiceId);
            ResultSet rs1 = stmt.executeQuery();
            ResultSet rs2 = stmt2.executeQuery();
            if(rs1.next()){
                invoice = new Invoice(rs1, rs2);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return invoice;
    }
}

