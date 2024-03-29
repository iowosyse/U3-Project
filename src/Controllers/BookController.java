package Controllers;
//Good to go.

import java.util.*;
import Project.*;
import Repositories.*;
import UtilityClasses.*;

public class BookController {
    /** Adds a new book to the books ArrayList */
    public static void createBook() {
        String aux;
        int aux2;
        Book newBook = new Book();
        Date bookDate;
        Author theAuthorOfThisBook;

        for (int i = 0; i < 1; i++) {
            AuthorController.showAuthors(false);
            System.out.println("Who wrote this book? \n(Enter the index of the author on the list).");
            System.out.println("Enter 0 to go back.");
            do {
                aux2 = ConsoleReader.readInteger();
            } while (aux2 - 1 > AuthorRepositories.authors.size() || aux2 < 0);

            if (aux2 != 0) {
                theAuthorOfThisBook = AuthorRepositories.authors.get(aux2 - 1);
                newBook.setAuthor(theAuthorOfThisBook);

                Book.idCounter ++; //better and easier, no need to generate random IDs
                newBook.setBookID();

                System.out.println("What's the title of the book?");
                aux = ConsoleReader.readString();
                newBook.setTitle(aux);

                System.out.println("When was it published?");
                bookDate = StuffCreator.createDate();
                newBook.setPublishDate(bookDate);

                System.out.println("What's the ISBN?");
                aux = ConsoleReader.readString();
                newBook.setISBN(aux);

                newBook.isAvailable = true;

                BookRepositories.books.add(newBook);
                BookRepositories.availableBooks.add(newBook);
                theAuthorOfThisBook.addWrittenBook(newBook);

                System.out.println(Colors.green + "Book created successfully!" + Colors.reset);
            } else {
                System.out.println("Going back...");
            }
        }
    }

    /**Gives options on how to show the books to make easier their reading*/
    public static int askHowToShow() { //makes the code easier to read
        int option;

        System.out.println("===============================");
        System.out.println("Show how?");
        System.out.println("1. Show available books");
        System.out.println("2. Show lent books");
        System.out.println("3. Show all books");
        System.out.print(">>");
        option = ConsoleReader.readInteger();
        System.out.println("===============================");

        return option;
    }

    /**Decides how to show the books and then shows them*/
    public static void showBooks(int option) {
        int count = 1;

        if (option == 1) {
            System.out.println("Showing available books to lend...");
            BookRepositories.showAvailableBooks();

        } else if (option == 2) {
            System.out.println("Showing lent books...");
            BookRepositories.showNotAvailableBooks();

        } else {
            System.out.println("Showing all books...");
            System.out.printf("| %-3s | %-22s | %-20s | %-10s |%n", "No.","Title", "Author", "Date");
            System.out.println("-------------------------------------------------------------------");
            for (Book showingBook : BookRepositories.books) {
                if (!showingBook.getTitle().isEmpty()) {
                    System.out.printf("| %-3s | %-22s | %-20s | %-10s |%n", count, showingBook.getTitle(), showingBook.getAuthor().getProfile().getName()
                            + " " + showingBook.getAuthor().getProfile().getLastName(), (showingBook.publishDate.getMonth() + 1) + "-" + showingBook.publishDate.getYear());
                    System.out.println("-------------------------------------------------------------------");
                    count ++;
                }
            }
        }
    }

    /** Modifies the selected attribute of a book */
    public static void updateBookData() {
        int option, aux;
        String auxStr;
        Author original, auxA;

        for (int i = 0; i < 1; i++) {
            showBooks(1);
            System.out.println(Colors.blue + "----------------------------------------------------------------------------------------");
            System.out.println("If you want to update a book that didn't show in the list it is highly possible " +
                    "that the book is in possession of a client");
            System.out.println("----------------------------------------------------------------------------------------" + Colors.reset);
            do {
                System.out.println("Which book do you want to update its data? ");
                System.out.println("(Enter 0 to go back.)");
                option = ConsoleReader.readInteger();
            } while (option - 1 > BookRepositories.availableBooks.size() || option < 0);

            if (option != 0) {
                Book toChange = BookRepositories.availableBooks.get(option - 1);

                System.out.println("===============================");
                System.out.println("What do you want to change?");
                System.out.println("1. Author.");
                System.out.println("2. Title.");
                System.out.println("3. Publish date.");
                System.out.println("4. ISBN.");
                System.out.println("0. Go back.");
                option = ConsoleReader.readInteger();
                System.out.println("===============================");

                switch (option) {
                    case 1 -> {
                        AuthorController.showAuthors(false);
                        do {
                            System.out.println("Who's the new author?");
                            aux = ConsoleReader.readInteger();
                        } while (aux - 1 > AuthorRepositories.authors.size() || aux < 0);

                        if (aux == 0) {
                            System.out.println("Going back...");
                            break;
                        }

                        original = toChange.getAuthor();
                        auxA = AuthorRepositories.authors.get(aux);

                        changeAuthor(original,auxA,toChange);
                    }
                    case 2 -> {
                        System.out.println("Enter the new title.");
                        System.out.print(">> ");
                        auxStr = ConsoleReader.readString();

                        toChange.setTitle(auxStr);
                    }
                    case 3 -> {
                        Date updatedDate = StuffCreator.createDate();
                        toChange.setPublishDate(updatedDate);
                    }
                    case 4 -> {
                        System.out.println("Enter the new ISBN.");
                        System.out.print(">> ");
                        auxStr = ConsoleReader.readString();

                        toChange.setISBN(auxStr);
                    }
                    case 0 -> System.out.println("Going back...");
                    default -> System.out.println(Colors.yellow + "Not an option." + Colors.reset);
                }
            } else {
                System.out.println("Going back...");
            }
        }
    }

    public static void deleteBook() {
        int option;
        Author theAuthor;
        Book toDelete;

        do {
            BookRepositories.showAvailableBooks();

            System.out.println(Colors.blue + "---If you want to delete a book that is not shown the client must return it.---" + Colors.reset);
            do {
                System.out.println("What book do you want to delete?");
                System.out.println("Enter 0 to go back.");
                option = ConsoleReader.readInteger();
            } while (option - 1 > BookRepositories.availableBooks.size() || option < 0);

            if (option != 0) {
                toDelete = BookRepositories.availableBooks.get(option - 1);

                theAuthor = toDelete.getAuthor();

                theAuthor.writtenBooks.remove(toDelete);
                BookRepositories.books.remove(toDelete);
                BookRepositories.availableBooks.remove(toDelete);
                System.out.println(Colors.green + "Book deleted successfully!" + Colors.reset);
            } else
                System.out.println("Going back...");
        } while (option != 0);
    }

    /**Do not the cat*/
    private static void changeAuthor(Author oldAuthor, Author newAuthor, Book toChange) {
        toChange.setAuthor(newAuthor);
        newAuthor.addWrittenBook(toChange);
        oldAuthor.deleteBook(toChange);
    }
}

