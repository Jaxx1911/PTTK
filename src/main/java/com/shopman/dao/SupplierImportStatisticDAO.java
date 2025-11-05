package com.shopman.dao;

import com.shopman.model.SupplierImportStatistic;
import com.shopman.model.Supplier;
import com.shopman.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierImportStatisticDAO {

    private Connection conn;

    public SupplierImportStatisticDAO() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<SupplierImportStatistic> getListSupplierImportStatistic(Date startDate, Date endDate) throws SQLException {
        List<SupplierImportStatistic> statistics = new ArrayList<>();
        String sql = "SELECT \n" +
                "    s.id,\n" +
                "    s.name,\n" +
                "    s.address,\n" +
                "    s.phone,\n" +
                "    s.email,\n" +
                "    COUNT(DISTINCT ii.id) AS import_count,\n" +
                "    SUM(ii.total_amount) AS total_amount,\n" +
                "    SUM(iip.total_quantity) AS total_quantity\n" +
                "FROM supplier s\n" +
                "JOIN (\n" +
                "    SELECT import_invoice_id, SUM(quantity) AS total_quantity\n" +
                "    FROM import_invoice_product\n" +
                "    GROUP BY import_invoice_id\n" +
                ") iip ON s.id = (SELECT supplier_id FROM import_invoice WHERE id = iip.import_invoice_id)\n" +
                "JOIN import_invoice ii ON s.id = ii.supplier_id\n" +
                "WHERE ii.import_date BETWEEN ? AND ?\n" +
                "GROUP BY s.id, s.name, s.address, s.phone, s.email\n" +
                "ORDER BY total_quantity DESC;\n";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SupplierImportStatistic statistic = new SupplierImportStatistic();
                statistic.setStartDate(startDate);
                statistic.setEndDate(endDate);
                statistic.setImportAmount((int) rs.getDouble("total_quantity"));
                statistic.setTotalImportPrice(rs.getFloat("total_amount"));
                statistic.setTotalImports(rs.getInt("import_count"));

                Supplier supplier = new Supplier();
                supplier.setId(rs.getInt("id"));
                supplier.setName(rs.getString("name"));
                supplier.setAddress(rs.getString("address"));
                supplier.setPhone(rs.getString("phone"));
                supplier.setEmail(rs.getString("email"));
                statistic.setSupplier(supplier);

                statistics.add(statistic);
            }
        }

        return statistics;
    }
}

