package com.mycompany.librarysystem;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private int type;

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

    public User(String username) {
        this.username = username;
    }

    public User(int id, String username, String password, String email, int type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    // Constructor, getters, setters, and database methods


}
