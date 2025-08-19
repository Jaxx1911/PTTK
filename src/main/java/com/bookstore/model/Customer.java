package com.bookstore.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Customer {
    private int id;
    private int userId;
    private String phone;
    private Date dateOfBirth;
    private int addressId;
    private Timestamp createdAt;
    
    // Associated objects
    private User user;
    private Address address;
    
    // Default constructor
    public Customer() {}
    
    // Constructor without id and timestamp
    public Customer(int userId, String phone, Date dateOfBirth, int addressId) {
        this.userId = userId;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.addressId = addressId;
    }
    
    // Constructor with all fields
    public Customer(int id, int userId, String phone, Date dateOfBirth, int addressId, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.addressId = addressId;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public int getAddressId() {
        return addressId;
    }
    
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", userId=" + userId +
                ", phone='" + phone + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", addressId=" + addressId +
                ", createdAt=" + createdAt +
                '}';
    }
} 