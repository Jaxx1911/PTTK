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
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.description = rs.getString("description");
        this.price = rs.getDouble("price");
        this.unit = rs.getString("unit");
    }

    public static Product fromResultSet(ResultSet rs) throws SQLException {
        if (rs == null) return null;
        int id = rs.getInt("id");
        String name = "";
        // try common column names
        try { name = rs.getString("name"); } catch (Exception ignored) {}

        String description = null;
        try { description = rs.getString("description"); } catch (Exception ignored) {}

        double price = 0;
        try { price = rs.getDouble("price"); } catch (Exception ignored) {}

        String unit = "";
        try { unit = rs.getString("unit"); } catch (Exception ignored) {}

        return new Product(id, name, description, price, unit);
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
