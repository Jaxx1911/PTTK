package com.shopman.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportInvoiceProduct {
    private int quantity;
    private float unitImportPrice;
    private float subTotal;
    private Product product;

    public ImportInvoiceProduct() {}

    public ImportInvoiceProduct(ResultSet rs) throws SQLException {
        this.setQuantity(rs.getInt("quantity"));
        this.setUnitImportPrice(rs.getFloat("unit_import_price"));
        this.setSubTotal(rs.getFloat("sub_total"));

        Product product = new Product(rs);

        this.setProduct(product);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getUnitImportPrice() {
        return unitImportPrice;
    }

    public void setUnitImportPrice(float unitImportPrice) {
        this.unitImportPrice = unitImportPrice;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

