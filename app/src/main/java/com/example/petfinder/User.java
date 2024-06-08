package com.example.petfinder;

public class User {
    String email, username, password, picture;

    public User(String email, String username, String password, String picture) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.picture = picture;

    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
