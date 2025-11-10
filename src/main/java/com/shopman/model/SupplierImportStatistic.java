package com.shopman.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SupplierImportStatistic extends Supplier {
    private float importAmount;
    private float totalImportPrice;
    private int totalImports;
    private java.util.Date startDate;
    private java.util.Date endDate;

    public SupplierImportStatistic() {}

    public SupplierImportStatistic(ResultSet rs, Date startDate, Date endDate) throws SQLException {
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setImportAmount((rs.getFloat("total_quantity")));
        this.setTotalImportPrice(rs.getFloat("total_amount"));
        this.setTotalImports(rs.getInt("count"));

        this.setId(rs.getInt("id"));
        this.setName(rs.getString("name"));
        this.setAddress(rs.getString("address"));
        this.setPhone(rs.getString("phone"));
        this.setEmail(rs.getString("email"));
    }

    public float getImportAmount() {
        return importAmount;
    }

    public void setImportAmount(float importAmount) {
        this.importAmount = importAmount;
    }

    public float getTotalImportPrice() {
        return totalImportPrice;
    }

    public void setTotalImportPrice(float totalImportPrice) {
        this.totalImportPrice = totalImportPrice;
    }

    public int getTotalImports() {
        return totalImports;
    }

    public void setTotalImports(int totalImports) {
        this.totalImports = totalImports;
    }

    public java.util.Date getStartDate() {
        return startDate;
    }

    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }

    public java.util.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }
}

