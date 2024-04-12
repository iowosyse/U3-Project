package Repositories;

import Project.Book;
import java.util.ArrayList;

public class BookRepositories {
    public static ArrayList<Book> books = new ArrayList<>();
    public static ArrayList<Book> availableBooks = new ArrayList<>();
    public static ArrayList<Book> notAvailableBooks = new ArrayList<>();

    //Auxiliary methods
    public static void setAvailableBooks (){
        for (Book available : books) {
            if (available.isAvailable()) {
                availableBooks.add(available);
            }
        }
    }

    public static void setNotAvailableBooks() {
        for (Book notAvailable : books) {
            if (!notAvailable.isAvailable()) {
                notAvailableBooks.add(notAvailable);
            }
        }
    }

    public static void showNotAvailableBooks() {
        int count = 1;

        System.out.printf("| %-3s | %-22s | %-20s | %-10s |%n", "No.","Title", "Author", "Date");
        System.out.println("-------------------------------------------------------------------");
        for (Book showingBook : notAvailableBooks) {
            if (!showingBook.isAvailable() && !showingBook.getTitle().isEmpty()) {
                System.out.printf("| %-3s | %-22s | %-20s | %-10s |%n", count, showingBook.getTitle(), showingBook.getAuthor().getProfile().getName()
                        + " " + showingBook.getAuthor().getProfile().getLastName(), (showingBook.getPublishDate().getMonth() + 1) + "-" + showingBook.getPublishDate().getYear());
                System.out.println("-------------------------------------------------------------------");
                count ++;
            }
        }
    }

    public static void showAvailableBooks() {
        int count = 1;

        System.out.printf("| %-3s | %-22s | %-20s | %-10s |%n", "No.","Title", "Author", "Date");
        System.out.println("-------------------------------------------------------------------");
        for (Book showingBook : availableBooks) {
            if (showingBook.isAvailable() && !showingBook.getTitle().isEmpty()) {
                System.out.printf("| %-3s | %-22s | %-20s | %-10s |%n", count, showingBook.getTitle(), showingBook.getAuthor().getProfile().getName()
                        + " " + showingBook.getAuthor().getProfile().getLastName(), (showingBook.getPublishDate().getMonth() + 1) + "-" + showingBook.getPublishDate().getYear());
                System.out.println("-------------------------------------------------------------------");
                count ++;
            }
        }
    }
}
