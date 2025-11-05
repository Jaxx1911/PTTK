package com.shopman.model;


public class SaleStaff extends User {
    public SaleStaff() {
        super();
    }

    public SaleStaff(int id, String username, String password, String role, String name, String email) {
        super(id, username, password, role, name, email);
    }
}