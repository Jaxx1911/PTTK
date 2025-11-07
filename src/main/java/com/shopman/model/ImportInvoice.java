package com.shopman.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImportInvoice {
    private int id;
    private Date importDate;
    private float totalAmount;
    private float totalQuantity;
    private List<ImportInvoiceProduct> importProductDetail;
    private Manager manager;
    private Supplier supplier;

    public ImportInvoice() {}

    public ImportInvoice(ResultSet rs) throws SQLException {
        this.setId(rs.getInt("id"));
        this.setImportDate(rs.getTimestamp("import_date"));
        this.setTotalAmount(rs.getFloat("total_amount"));
        this.setTotalQuantity(rs.getFloat("total_quantity"));
    }

    public ImportInvoice(ResultSet rs, ResultSet rs2) throws SQLException {
        this.setId(rs.getInt("id"));
        this.setImportDate(rs.getTimestamp("import_date"));
        this.setTotalAmount(rs.getFloat("total_amount"));
        this.setTotalQuantity(rs.getFloat("total_quantity"));
        Supplier supplier = new Supplier();
        supplier.setId(rs.getInt("supplier_id"));
        supplier.setName(rs.getString("supplier_name"));
        supplier.setAddress(rs.getString("address"));
        supplier.setPhone(rs.getString("phone"));
        supplier.setEmail(rs.getString("email"));
        this.setSupplier(supplier);

        Manager manager = new Manager();
        manager.setId(rs.getInt("manager_id"));
        manager.setName(rs.getString("manager_name"));
        this.setManager(manager);
        List<ImportInvoiceProduct> products = new ArrayList<>();
        while (rs2.next()) {
            ImportInvoiceProduct item = new ImportInvoiceProduct(rs2);
            products.add(item);
        }
        this.setImportProductDetail(products);
    }

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

    public float getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(float totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}

