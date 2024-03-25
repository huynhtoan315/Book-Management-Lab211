package org.example;

public class Book {
    private String bookCode;
    private String bookName;
    private String bookTitle;
    private String bookDescription;
    private String bookType;
    private double bookPrice;

    public Book(String bookCode, String bookName, String bookTitle, String bookDescription, String bookType, double bookPrice) {
        this.bookCode = bookCode;
        this.bookName = bookName;
        this.bookTitle = bookTitle;
        this.bookDescription = bookDescription;
        this.bookType = bookType;
        this.bookPrice = bookPrice;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }
}
