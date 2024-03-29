package Project;

import java.util.*;

public class Book {
    public static int idCounter = 6;
    private String ISBN;
    private String title;
    public boolean isAvailable;
    public Date publishDate;
    private String bookID = "B|";
    private Author author;

    public Book() {

    }

    public Book(String ISBN, String title, boolean isAvailable, Date publishDate, String bookID, Author author) {
        this.ISBN = ISBN;
        this.title = title;
        this.isAvailable = isAvailable;
        this.publishDate = publishDate;
        this.bookID = bookID;
        this.author = author;
    }

    public void setBookID() {
        bookID += idCounter;
    }

    public String getBookID() {
        return bookID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setPublishDate(Date date) {
        publishDate = date;
    }
}
