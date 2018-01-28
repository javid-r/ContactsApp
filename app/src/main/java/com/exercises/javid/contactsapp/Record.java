package com.exercises.javid.contactsapp;

/**
 * Created by Javid on 1/28/2018.
 */

public class Record {
    private String name, lastName, phone, email, address, desc;

    public Record() {
    }

    public Record(String name, String lastName, String phone, String email, String address, String desc) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }




}
