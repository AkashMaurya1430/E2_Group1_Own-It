package com.example.ownit;

public class User {
    private String userId;
    private String contact;
    private String password;
    private String name;
    private String message;

    public String getUserId() {
        return userId;
    }
    public String getMessage() {
        return message;
    }

    public User(String name,String contact, String password) {
        this.name = name;
        this.contact = contact;
        this.password = password;
    }

    public User(String contact, String password) {
        this.contact = contact;
        this.password = password;
    }



    public String getContact() {
        return contact;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
