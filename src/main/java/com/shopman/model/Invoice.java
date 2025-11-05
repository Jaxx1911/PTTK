package com.shopman.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Invoice {
    private int id;
    private Date date;
    private float totalAmount;
    private String status;
    private Customer customer;
    private List<InvoiceProduct> productsDetail;
    private SaleStaff saleStaff;
    private DeliveryStaff deliveryStaff;

    public Invoice() {
        this.productsDetail = new ArrayList<>();
    }

    public Invoice(int id, Date date, float totalAmount, String status, Customer customer, List<InvoiceProduct> productsDetail) {
        this.id = id;
        this.date = date;
        this.totalAmount = totalAmount;
        this.status = status;
        this.customer = customer;
        this.productsDetail = productsDetail;
    }

    public Invoice(int id, Date date, float totalAmount, String status, Customer customer) {
        this.id = id;
        this.date = date;
        this.totalAmount = totalAmount;
        this.status = status;
        this.customer = customer;
    }

    public List<InvoiceProduct> getProducts() {
        return productsDetail;
    }

    public void setProducts(List<InvoiceProduct> products) {
        this.productsDetail = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<InvoiceProduct> getProductsDetail() {
        return productsDetail;
    }

    public void setProductsDetail(List<InvoiceProduct> productsDetail) {
        this.productsDetail = productsDetail;
    }

    public SaleStaff getSaleStaff() {
        return saleStaff;
    }

    public void setSaleStaff(SaleStaff saleStaff) {
        this.saleStaff = saleStaff;
    }

    public DeliveryStaff getDeliveryStaff() {
        return deliveryStaff;
    }

    public void setDeliveryStaff(DeliveryStaff deliveryStaff) {
        this.deliveryStaff = deliveryStaff;
    }
}

