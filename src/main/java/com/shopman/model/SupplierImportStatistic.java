package com.shopman.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SupplierImportStatistic {
    private int importAmount;
    private float totalImportPrice;
    private int totalImports;
    private java.util.Date startDate;
    private java.util.Date endDate;
    private Supplier supplier;

    public SupplierImportStatistic() {}

    public SupplierImportStatistic(ResultSet rs, Date startDate, Date endDate) throws SQLException {
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setImportAmount((int) rs.getDouble("total_quantity"));
        this.setTotalImportPrice(rs.getFloat("total_amount"));
        this.setTotalImports(rs.getInt("count"));

        Supplier supplier = new Supplier();
        supplier.setId(rs.getInt("id"));
        supplier.setName(rs.getString("name"));
        supplier.setAddress(rs.getString("address"));
        supplier.setPhone(rs.getString("phone"));
        supplier.setEmail(rs.getString("email"));
        this.setSupplier(supplier);
    }

    public int getImportAmount() {
        return importAmount;
    }

    public void setImportAmount(int importAmount) {
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

