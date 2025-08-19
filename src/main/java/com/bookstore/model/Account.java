package com.bookstore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Account {
    private int id;
    private int customerId;
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal balance;
    private boolean isActive;
    private Timestamp createdAt;
    
    // Associated objects
    private Customer customer;
    
    // Enum for account types
    public enum AccountType {
        SAVINGS, CHECKING, CREDIT
    }
    
    // Default constructor
    public Account() {}
    
    // Constructor without id and timestamp
    public Account(int customerId, String accountNumber, AccountType accountType, BigDecimal balance, boolean isActive) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.isActive = isActive;
    }
    
    // Constructor with all fields
    public Account(int id, int customerId, String accountNumber, AccountType accountType, 
                   BigDecimal balance, boolean isActive, Timestamp createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.isActive = isActive;
        this.createdAt = createdAt;
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
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public AccountType getAccountType() {
        return accountType;
    }
    
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType=" + accountType +
                ", balance=" + balance +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
} 