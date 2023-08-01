package com.example.project;

public class Books {
    private int id;
    private String BookName;

    public Books(int id, String BookName) {
        this.id = id;
        this.BookName = BookName;
    }

    public int getId() {
        return id;
    }

    public String getBookName() {
        return BookName;
    }
}
