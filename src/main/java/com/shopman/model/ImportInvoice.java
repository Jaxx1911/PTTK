package com.shopman.model;

import java.util.Date;
import java.util.List;

public class ImportInvoice {
    private int id;
    private Date importDate;
    private float totalAmount;
    private List<ImportInvoiceProduct> importProductDetail;
    private Manager manager;
    private Supplier supplier;

    public ImportInvoice() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<ImportInvoiceProduct> getImportProductDetail() {
        return importProductDetail;
    }

    public void setImportProductDetail(List<ImportInvoiceProduct> importProductDetail) {
        this.importProductDetail = importProductDetail;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

