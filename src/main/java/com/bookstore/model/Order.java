package com.bookstore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Order {
    private int id;
    private int customerId;
    private Timestamp orderDate;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private int shippingAddressId;
    private String paymentMethod;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Associated objects
    private Customer customer;
    private Address shippingAddress;
    private List<OrderItem> orderItems;
    
    // Enum for order status
    public enum OrderStatus {
        PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    }
    
    // Default constructor
    public Order() {}
    
    // Constructor without id and timestamps
    public Order(int customerId, BigDecimal totalAmount, OrderStatus status, 
                 int shippingAddressId, String paymentMethod) {
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.shippingAddressId = shippingAddressId;
        this.paymentMethod = paymentMethod;
    }
    
    // Constructor with all fields
    public Order(int id, int customerId, Timestamp orderDate, BigDecimal totalAmount, 
                 OrderStatus status, int shippingAddressId, String paymentMethod, 
                 Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.shippingAddressId = shippingAddressId;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public Timestamp getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public OrderStatus getStatus() {
        return status;
    }
    
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
    public int getShippingAddressId() {
        return shippingAddressId;
    }
    
    public void setShippingAddressId(int shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Address getShippingAddress() {
        return shippingAddress;
    }
    
    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", shippingAddressId=" + shippingAddressId +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
} 