package com.shopman.model;

public class Customer extends User {
    private String phone;
    private String address;

    public Customer() {
        super();
    }

    public Customer(int id, String username, String password, String role, String name, String email, String phone, String address) {
        super(id, username, password, role, name, email);
        this.phone = phone;
        this.address = address;
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

