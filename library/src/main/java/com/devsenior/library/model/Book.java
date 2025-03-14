package com.devsenior.library.model;

public class Book {
    private final String id, title, author;
    private boolean isBorrowed;

    public Book(String id, String title, String author, boolean isBorrowed) {
        this.id = id;
        this.title = title;
        this.author = author;
        isBorrowed = false;
    }

    public String getId() {return id;}
    public String getTitle() {return title;}
    public String getAuthor() {return author;}

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }
}
