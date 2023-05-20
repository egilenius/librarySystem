package com.mycompany.librarysystem;

public class Item {
    private int itemid;
    private String title;
    private String author;
    private String genre;
    private String location;
    private boolean available;

    public Item(int itemid, String title, String author, String genre) {
        this.itemid = itemid;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.available = true;
    }

    public Item(int itemid, String title, String author, String genre, String location, boolean available) {
        this.itemid = itemid;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.location = location;
        this.available = available;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Constructor, getters, setters, and database methods
}
