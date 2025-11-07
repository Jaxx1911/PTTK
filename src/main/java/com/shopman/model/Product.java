package com.shopman.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String unit;

    public Product() {}

    public Product(int id, String name, String description, double price, String unit) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.unit = unit;
    }

    public Product(ResultSet rs) throws SQLException {
        this.setId(rs.getInt("product_id"));
        this.setName(rs.getString("name"));
        this.setDescription(rs.getString("description"));
        this.setPrice((float) rs.getDouble("price"));
        this.setUnit(rs.getString("unit"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
