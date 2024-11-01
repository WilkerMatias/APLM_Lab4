package com.example.lab4;

public class User {
    private long id;
    private String password;
    private String username;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
