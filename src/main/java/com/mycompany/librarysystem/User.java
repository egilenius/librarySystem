package com.mycompany.librarysystem;

import java.util.UUID;

public class User {
    private UUID userid;
    private String username;
    private String password;
    private String email;
    private int type;
    private int allowedloans;
    private int presentloans;

    String[][] bookData = {
        {"The Great Gatsby", "no", "2023-05-10"}, //loan
        {"To Kill a Mockingbird", "no", "2023-05-12"}, //loan
        {"Pride and Prejudice", "yes", "2023-04-20"}, //finished
        {"1984", "no", "2023-05-15"}, //loan
        {"The Catcher in the Rye", "yes", "2023-04-22"}, //finished
        {"Animal Farm", "yes", "2023-04-25"}, // finished
        {"Brave New World", "no", "2023-03-20"}, //overdue
        {"The Hobbit", "no", "2023-03-08"}, //overdue
        {"Lord of the Flies", "yes", "2023-04-24"}, // finished
        {"The Da Vinci Code", "no", "2023-05-18"} //loan
    };


    public User(String username, UUID userid, int allowedloans, int presentloans) {
        this.username = username;
        this.userid = userid;
        this.allowedloans = allowedloans;
        this.presentloans = presentloans;
    }

    public UUID getUserid() {
        return userid;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAllowedloans() {
        return allowedloans;
    }

    public int getPresentloans() {
        return presentloans;
    }

    // Constructor, getters, setters, and database methods


}
