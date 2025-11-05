package com.shopman.model;

public class DeliveryStaff extends User {
    public DeliveryStaff() {
        super();
    }

    public DeliveryStaff(int id, String username, String password, String role, String name, String email) {
        super(id, username, password, role, name, email);
    }
}