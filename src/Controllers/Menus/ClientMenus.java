package Controllers.Menus;

import Controllers.BookController;
import Project.Client;
import Project.Book;
import Repositories.BookRepositories;
import UtilityClasses.Colors;
import UtilityClasses.ConsoleReader;


public class ClientMenus {
    public static void clientMainMenu(Client activeUser) {
        int opt;

        do {
            System.out.println("1. Create token.");
            System.out.println("2. Show all of your transactions.");
            System.out.println("0. Exit.");

            opt = ConsoleReader.readInteger();

            switch (opt) {
                case 1 -> {
                    if (activeUser.canAsk()) {
                        System.out.println("===============================");
                        System.out.println("\t  ---Menus---");
                        System.out.println("1. Create a token to ask for a book");
                        System.out.println("2. Create a token to return a book");
                        System.out.println("0. Go back.");
                        opt = ConsoleReader.readInteger();
                        System.out.println("===============================");

                        Book tokenedBook;

                        switch (opt) {
                            case 1 -> {
                                BookController.showBooks(1);
                                do {
                                    System.out.println("What book do you want to borrow?");
                                    System.out.println("Enter 0 to go back.");
                                    opt = ConsoleReader.readInteger();
                                } while (opt - 1 > BookRepositories.availableBooks.size() || opt < 0);

                                if (opt != 0) {
                                    tokenedBook = BookRepositories.availableBooks.get(opt - 1);
                                    activeUser.createToken(tokenedBook,activeUser, "Borrow");
                                    System.out.println(Colors.green + "Token created successfully!" + Colors.reset);
                                    System.out.println(Colors.blue + "Wait for an admin to validate your new token. When the token gets validated " +
                                            "the transaction will appear in your transactions." + Colors.reset);
                                }
                            } case 2 -> {
                                activeUser.showBorrowedBooks();
                                do {
                                    System.out.println("What book do you want to return");
                                    System.out.println("Enter 0 to go back.");
                                    opt = ConsoleReader.readInteger();
                                } while (opt - 1 > activeUser.getBorrowedBooks().size() || opt < 0);

                                if (opt != 0) {
                                    tokenedBook = activeUser.getBorrowedBooks().get(opt - 1);
                                    activeUser.createToken(tokenedBook, activeUser, "Return");
                                    System.out.println(Colors.green + "Token created successfully!" + Colors.reset);
                                    System.out.println(Colors.blue + "Wait for an admin to validate your new token. When the token gets validated " +
                                            "the transaction will appear in your transactions." + Colors.reset);
                                }
                            } case 0 -> System.out.println("Going back...");
                            default -> System.out.println(Colors.yellow + "Not an option." + Colors.reset);
                        }
                    } else
                        System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                }
                case 2 -> {
                    if (activeUser.canRead())
                        activeUser.showTransactions(activeUser);
                    else
                        System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                }
                case 0 -> System.out.println("Going back...");
                default -> System.out.println(Colors.yellow + "Not an option." + Colors.reset);
            }
        } while (opt != 0);
    }
}
