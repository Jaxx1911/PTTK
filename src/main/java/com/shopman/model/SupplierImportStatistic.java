package com.shopman.model;

public class SupplierImportStatistic {
    private int importAmount;
    private float totalImportPrice;
    private int totalImports;
    private java.util.Date startDate;
    private java.util.Date endDate;
    private Supplier supplier;

    public SupplierImportStatistic() {}

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

