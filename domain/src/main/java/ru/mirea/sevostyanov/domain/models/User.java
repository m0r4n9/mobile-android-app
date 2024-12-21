package ru.mirea.sevostyanov.domain.models;

public class User {
    private String id;
    private String email;
    private String username;
    private boolean isAuthenticated;

    public User(String id, String email, String username, boolean isAuthenticated) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.isAuthenticated = isAuthenticated;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }
}