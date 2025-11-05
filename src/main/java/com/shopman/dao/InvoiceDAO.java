package com.shopman.dao;

import com.shopman.model.*;
import com.shopman.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {
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
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setDate(rs.getTimestamp("date"));
                invoice.setTotalAmount((float) rs.getDouble("total_amount"));
                invoice.setStatus(rs.getString("status"));

                // Tạo customer object
                Customer customer = new Customer();
                customer.setId(customerId);
                invoice.setCustomer(customer);

                invoices.add(invoice);
            }
        }

        return invoices;
    }

    public Invoice getInvoiceDetail(int invoiceId) throws SQLException {
        Invoice invoice = new Invoice();
        List<InvoiceProduct> products = new ArrayList<>();
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

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql1)) {

            stmt.setInt(1, invoiceId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                invoice.setId(rs.getInt("id"));
                invoice.setDate(rs.getTimestamp("date"));
                invoice.setTotalAmount((float) rs.getDouble("total_amount"));
                invoice.setStatus(rs.getString("status"));

                // Tạo customer object
                Customer customer = new Customer();
                customer.setId(rs.getInt("customer_id"));
                customer.setName(rs.getString("customer_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                invoice.setCustomer(customer);

                // Tạo SaleStaff object nếu có
                int saleStaffId = rs.getInt("sale_staff_id");
                if (!rs.wasNull() && saleStaffId > 0) {
                    SaleStaff saleStaff = new SaleStaff();
                    saleStaff.setId(saleStaffId);
                    saleStaff.setName(rs.getString("sale_staff_name"));
                    invoice.setSaleStaff(saleStaff);
                }

                // Tạo DeliveryStaff object nếu có
                int deliveryStaffId = rs.getInt("delivery_staff_id");
                if (!rs.wasNull() && deliveryStaffId > 0) {
                    DeliveryStaff deliveryStaff = new DeliveryStaff();
                    deliveryStaff.setId(deliveryStaffId);
                    deliveryStaff.setName(rs.getString("delivery_staff_name"));
                    invoice.setDeliveryStaff(deliveryStaff);
                }
            }
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql2)) {

            stmt.setInt(1, invoiceId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                InvoiceProduct invoiceProduct = new InvoiceProduct(rs);
                invoiceProduct.setQuantity(rs.getInt("quantity"));
                invoiceProduct.setUnitPrice(rs.getDouble("unit_price"));
                invoiceProduct.setSubtotal(rs.getDouble("sub_total"));

                products.add(invoiceProduct);
            }

            invoice.setProducts(products);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return invoice;
    }
}

