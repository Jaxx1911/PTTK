package com.shopman.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer extends User {
    private String phone;
    private String address;

    public Customer() {
        super();
    }

    public Customer(ResultSet rs) throws SQLException {
        this.setId(rs.getInt("customer_id"));
        this.setName(rs.getString("customer_name"));
        this.setEmail(rs.getString("email"));
        this.setPhone(rs.getString("phone"));
    }

    public Customer(int id, String username, String password, String role, String name, String email, String phone, String address) {
        super(id, username, password, role, name, email);
        this.phone = phone;
        this.address = address;
    }

    public Customer(int id){
        super();
        this.setId(id);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

