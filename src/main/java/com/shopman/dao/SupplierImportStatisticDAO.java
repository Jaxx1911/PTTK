package com.shopman.dao;

import com.shopman.model.SupplierImportStatistic;
import com.shopman.model.Supplier;
import com.shopman.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierImportStatisticDAO extends DAO {
    public SupplierImportStatisticDAO() {
        super();
    }

    public List<SupplierImportStatistic> getListSupplierImportStatistic(Date startDate, Date endDate) throws SQLException {
        List<SupplierImportStatistic> statistics = new ArrayList<>();
        String sql =
                "SELECT ii.supplier_id as id, s.name as name, s.address as address, s.email as email, s.phone as phone, sum(ii.total_amount) as total_amount, sum(ii.total_quantity) as total_quantity, count(ii.id) as count\n" +
                        "FROM import_invoice ii\n" +
                        "JOIN supplier s ON s.id = ii.supplier_id\n" +
                        "WHERE ii.import_date BETWEEN ? AND ?\n" +
                        "GROUP BY ii.supplier_id\n" +
                        "ORDER BY sum(ii.total_quantity) DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SupplierImportStatistic statistic = new SupplierImportStatistic(rs, startDate, endDate);

                statistics.add(statistic);
            }
        }

        return statistics;
    }
}

