package com.shopman.dao;

import com.shopman.model.*;
import com.shopman.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImportInvoiceDAO {

    private Connection conn;

    public ImportInvoiceDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ImportInvoice> getListImportInvoice(int supplierId, Date startDate, Date endDate) throws SQLException {
        List<ImportInvoice> invoices = new ArrayList<>();
        String sql = "SELECT ii.id, ii.import_date, ii.total_amount " +
                     "FROM import_invoice ii " +
                     "WHERE ii.supplier_id = ? AND ii.import_date BETWEEN ? AND ? " +
                     "ORDER BY ii.import_date DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplierId);
            stmt.setDate(2, startDate);
            stmt.setDate(3, endDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ImportInvoice invoice = new ImportInvoice();
                invoice.setId(rs.getInt("id"));
                invoice.setImportDate(rs.getTimestamp("import_date"));
                invoice.setTotalAmount(rs.getFloat("total_amount"));
                invoices.add(invoice);
            }
        }

        return invoices;
    }

    public ImportInvoice getImportInvoiceDetail(int importInvoiceId) throws SQLException {
        ImportInvoice invoice = null;
        String sql = "SELECT ii.id, ii.import_date, ii.total_amount, " +
                     "s.id as supplier_id, s.name as supplier_name, s.address, s.phone, s.email, " +
                     "u.id as manager_id, u.name as manager_name " +
                     "FROM import_invoice ii " +
                     "JOIN supplier s ON ii.supplier_id = s.id " +
                     "JOIN user u ON ii.manager_id = u.id " +
                     "WHERE ii.id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, importInvoiceId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                invoice = new ImportInvoice();
                invoice.setId(rs.getInt("id"));
                invoice.setImportDate(rs.getTimestamp("import_date"));
                invoice.setTotalAmount(rs.getFloat("total_amount"));

                Supplier supplier = new Supplier();
                supplier.setId(rs.getInt("supplier_id"));
                supplier.setName(rs.getString("supplier_name"));
                supplier.setAddress(rs.getString("address"));
                supplier.setPhone(rs.getString("phone"));
                supplier.setEmail(rs.getString("email"));
                invoice.setSupplier(supplier);

                Manager manager = new Manager();
                manager.setId(rs.getInt("manager_id"));
                manager.setName(rs.getString("manager_name"));
                invoice.setManager(manager);

                invoice.setImportProductDetail(getImportInvoiceProducts(importInvoiceId));
            }
        }

        return invoice;
    }

    private List<ImportInvoiceProduct> getImportInvoiceProducts(int invoiceId) throws SQLException {
        List<ImportInvoiceProduct> products = new ArrayList<>();
        String sql = "SELECT iip.quantity, iip.unit_import_price, iip.sub_total, " +
                     "p.id as product_id, p.name, p.description, p.price, p.unit " +
                     "FROM import_invoice_product iip " +
                     "JOIN product p ON iip.product_id = p.id " +
                     "WHERE iip.import_invoice_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, invoiceId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ImportInvoiceProduct item = new ImportInvoiceProduct();
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitImportPrice(rs.getFloat("unit_import_price"));
                item.setSubTotal(rs.getFloat("sub_total"));

                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setUnit(rs.getString("unit"));
                item.setProduct(product);

                products.add(item);
            }
        }

        return products;
    }
}

