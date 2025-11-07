package com.shopman.model;

import java.sql.ResultSet;

public class InvoiceProduct {
    private Product product;
    private float quantity;
    private double unitPrice;
    private double subtotal;

    public InvoiceProduct() {}

    public InvoiceProduct(Product product, int quantity, double unitPrice, double subtotal) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
    }

    public InvoiceProduct(ResultSet rs) throws Exception {
        this.product = new Product(rs);

        this.setQuantity(rs.getInt("quantity"));
        this.setUnitPrice(rs.getDouble("unit_price"));
        this.setSubtotal(rs.getDouble("sub_total"));
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}

