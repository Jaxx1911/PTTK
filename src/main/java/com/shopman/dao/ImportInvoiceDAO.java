package com.shopman.dao;

import com.shopman.model.*;
import com.shopman.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImportInvoiceDAO extends DAO {
    public ImportInvoiceDAO() {
        super();
    }

    public List<ImportInvoice> getListImportInvoice(int supplierId, Date startDate, Date endDate) throws SQLException {
        List<ImportInvoice> invoices = new ArrayList<>();
        String sql = "SELECT ii.id, ii.import_date, ii.total_amount, ii.total_quantity " +
                "FROM import_invoice ii " +
                "WHERE ii.supplier_id = ? AND ii.import_date BETWEEN ? AND ? " +
                "ORDER BY ii.import_date DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplierId);
            stmt.setDate(2, startDate);
            stmt.setDate(3, endDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ImportInvoice invoice = new ImportInvoice(rs);
                invoices.add(invoice);
            }
        }

        return invoices;
    }

    public ImportInvoice getImportInvoiceDetail(int importInvoiceId) throws SQLException {
        ImportInvoice invoice = null;
        String sql1 = "SELECT ii.id, ii.import_date, ii.total_amount, ii.total_quantity, " +
                "s.id as supplier_id, s.name as supplier_name, s.address, s.phone, s.email, " +
                "u.id as manager_id, u.name as manager_name " +
                "FROM import_invoice ii " +
                "JOIN supplier s ON ii.supplier_id = s.id " +
                "JOIN user u ON ii.manager_id = u.id " +
                "WHERE ii.id = ?";

        String sql2 = "SELECT iip.quantity, iip.unit_import_price, iip.sub_total, " +
                "p.id as product_id, p.name, p.description, p.price, p.unit " +
                "FROM import_invoice_product iip " +
                "JOIN product p ON iip.product_id = p.id " +
                "WHERE iip.import_invoice_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql1); PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
            stmt.setInt(1, importInvoiceId);
            ResultSet rs = stmt.executeQuery();
            stmt2.setInt(1, importInvoiceId);
            ResultSet rs2 = stmt2.executeQuery();

            if (rs.next()) {
                invoice = new ImportInvoice(rs, rs2);
            }
        }

        return invoice;
    }
}

