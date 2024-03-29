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
            System.out.println("1. Create token to ask for a book.");
            System.out.println("2. Show all of your transactions.");
            System.out.println("0. Exit.");

            opt = ConsoleReader.readInteger();

            switch (opt) {
                case 1 -> {
                    Book askingFor;

                    BookController.showBooks(1);
                    do {
                        System.out.println("What book do you want to borrow?");
                        System.out.println("Enter 0 to go back.");
                        opt = ConsoleReader.readInteger();
                    } while (opt - 1 > BookRepositories.availableBooks.size() || opt < 0);

                    if (opt != 0) {
                        askingFor = BookRepositories.availableBooks.get(opt - 1);
                        activeUser.createToken(askingFor,activeUser);
                    }
                }
                case 2 -> activeUser.showTransactions(activeUser);
                case 0 -> System.out.println("Going back...");
                default -> System.out.println(Colors.yellow + "Not an option." + Colors.reset);
            }
        } while (opt != 0);
    }
}
