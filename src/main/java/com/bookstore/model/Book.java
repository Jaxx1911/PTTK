package com.bookstore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Book {
    private int id;
    private String title;
    private String author;
    private BigDecimal price;
    private String description;
    private String category;
    private String isbn;
    private int stockQuantity;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Default constructor
    public Book() {}
    
    // Constructor without id and timestamps
    public Book(String title, String author, BigDecimal price, String description, String category, String isbn, int stockQuantity) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.category = category;
        this.isbn = isbn;
        this.stockQuantity = stockQuantity;
    }
    
    // Constructor with all fields
    public Book(int id, String title, String author, BigDecimal price, String description, String category, 
                String isbn, int stockQuantity, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.category = category;
        this.isbn = isbn;
        this.stockQuantity = stockQuantity;
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
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public int getStockQuantity() {
        return stockQuantity;
    }
    
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
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
    
    public boolean isInStock() {
        return stockQuantity > 0;
    }
    
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", isbn='" + isbn + '\'' +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
} 