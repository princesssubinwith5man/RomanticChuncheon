package com.example.practice;

public class User {
    public String name;
    public String sector;
    public String telnum;
    public String address;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String sector, String telnum, String address) {
        this.name = name;
        this.sector = sector;
        this.telnum = telnum;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", sector='" + sector + '\'' +
                ", telnum='" + telnum + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
