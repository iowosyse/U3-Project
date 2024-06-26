package Controllers.Menus;

import Controllers.*;
import Entities.*;
import UtilityClasses.Colors;
import UtilityClasses.ConsoleReader;

public class AdminMenus {

    /** THE GAME
     * Shows everything an admin can do.
     * @param activeUser is the one who's interacting with the program.*/
    public static void adminMainMenu(Admin activeUser) { // first menu to show up
        int option;

        do {
            System.out.println("===============================");
            System.out.println("\t  ---Menus---");
            System.out.println("1. Access books menu.");
            System.out.println("2. Access authors menu.");
            System.out.println("3. Access users menu.");
            System.out.println("4. Access transactions menu.");
            System.out.println("0. Go back.");
            option = ConsoleReader.readInteger();
            System.out.println("===============================");

            switch (option) {
                case 1 -> AdminMenus.adminBookMenu(activeUser);
                case 2 -> AdminMenus.authorMenu(activeUser);
                case 3 -> AdminMenus.userMenu(activeUser);
                case 4 -> AdminMenus.transactionMenu(activeUser);
                case 0 -> System.out.println("Going back...");
                default -> System.out.println(Colors.yellow + "Not an option." + Colors.reset);
            }
        } while (option != 0);
    }

    /**Shows all the options an admin has to manipulate the books
     * @param activeAdmin the one who's interacting with the program.*/
    public static void adminBookMenu(Admin activeAdmin) {
        int option;

        do {
            System.out.println("===============================");
            System.out.println("1. Create a book.");
            System.out.println("2. Show books.");
            System.out.println("3. Update a book's data.");
            System.out.println("4. Delete a book.");
            System.out.println("0. Go back.");
            option = ConsoleReader.readInteger();
            System.out.println("===============================");

            switch (option) {
                case 1 -> {
                    if (activeAdmin.canWrite())
                        BookController.createBook();
                    else
                        System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                }
                case 2 -> {
                    if (activeAdmin.canRead())
                        BookController.showBooks(BookController.askHowToShow());
                    else
                        System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                }
                case 3 -> {
                    if (activeAdmin.canWrite() && activeAdmin.canRead())
                        BookController.updateBookData();
                    else
                        System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                }
                case 4 -> {
                    if (activeAdmin.canDelete() && activeAdmin.canRead())
                        BookController.deleteBook();
                    else
                        System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                }
                case 0 -> System.out.println("Going back...");
                default -> System.out.println(Colors.yellow + "Not an option." + Colors.reset);
            }
        } while (option != 0);
    }

    /**Treats the author just as another profile, except this type of profile can be assigned as authors of books*/
    public static void authorMenu(Admin activeAdmin) {
        int option;

        do {
            System.out.println("===============================");
            System.out.println("1. Create an author.");
            System.out.println("2. Show authors.");
            System.out.println("3. Update an author's profile.");
            System.out.println("4. Delete an author.");
            System.out.println("0. Go back.");
            option = ConsoleReader.readInteger();
            System.out.println("===============================");

            //every method has documentation, please read it if you don't know how something works
            switch (option) {
                case 1 -> {
                    if (activeAdmin.canWrite())
                        AuthorController.createAuthor();
                    else
                        System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                }
                case 2 -> {
                    if (activeAdmin.canRead())
                        AuthorController.showAuthors(true);
                    else
                        System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                }
                case 3 -> {
                    if (activeAdmin.canWrite() && activeAdmin.canRead())
                        AuthorController.updateAuthorData();
                    else
                        System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                }
                case 4 -> {
                    if (activeAdmin.canDelete() && activeAdmin.canRead())
                        AuthorController.deleteAuthor();
                    else
                        System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                }
                case 0 -> System.out.println("Going back...");
                default -> System.out.println(Colors.yellow + "Not an option." + Colors.reset);
            }
        } while (option != 0);
    }

    /**General profile manager, CRUDs and stuff*/
    public static void userMenu(Admin activeAdmin) {
        int option;

        do {
            System.out.println("===============================");
            System.out.println("1. Create a new User profile");
            System.out.println("2. Show users.");
            System.out.println("3. Update an user's data.");
            System.out.println("4. Delete an user.");
            System.out.println("0. Go back.");
            option = ConsoleReader.readInteger();
            System.out.println("===============================");

            switch (option) {
                case 1 -> {
                    if (activeAdmin.canWrite()) {
                        System.out.println("1. Create an admin.\n2. Create a client.");
                        option = ConsoleReader.readInteger();
                        switch (option) {
                            case 1 -> AdminController.createAdmin();
                            case 2 -> ClientController.createClient();
                            default -> System.out.println(Colors.yellow + "Not an option" + Colors.reset);
                        }
                    } else
                        System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                }
                case 2 -> {
                    if (activeAdmin.canRead()) {
                        System.out.println("1. Show admins");
                        System.out.println("2. Show clients");
                        System.out.println("3. Show all");
                        int showOption = ConsoleReader.readInteger();

                        switch (showOption) {
                            case 1 -> AdminController.showAdmins();
                            case 2 -> ClientController.showClients(true);
                            case 3 -> {
                                System.out.println("Admins: ");
                                AdminController.showAdmins();
                                System.out.println("Clients: ");
                                ClientController.showClients(true);
                            }
                        }
                    } else
                        System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                }
                case 3 -> {
                    if (activeAdmin.canWrite() && activeAdmin.canRead())
                        UserControllers.updateUserData(activeAdmin);
                    else
                        System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                }
                case 4 -> {
                    if (activeAdmin.canDelete() && activeAdmin.canRead()) {
                        System.out.println("1. Delete an admin\n2. Delete a client\n0. Go back");
                        option = ConsoleReader.readInteger();

                        switch (option) {
                            case 1 -> AdminController.deleteAdmin(activeAdmin);
                            case 2 -> ClientController.deleteClient();
                        }
                    } else
                        System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                }
                case 0 -> System.out.println("Going back...");
                default -> System.out.println(Colors.yellow + "Not an option." + Colors.reset);
            }


        } while (option != 0);
    }

    /**The program revolves around this menu and its option, can lend and return books*/
    public static void transactionMenu(Admin activeAdmin) {
        int option;

        do {
            System.out.println("===============================");
            System.out.println("1. Tokens.");
            System.out.println("2. Create transaction.");
            System.out.println("3. Show transactions.");
            System.out.println("0. Go back.");
            option = ConsoleReader.readInteger();
            System.out.println("===============================");

            switch (option) {
                case 1 -> {
                    if (activeAdmin.canWrite() && activeAdmin.canRead()) {
                        System.out.println("1. Validate token.");
                        System.out.println("2. Show token history");
                        System.out.println("0. Go back.");
                        option = ConsoleReader.readInteger();

                        switch (option) {
                            case 1 -> TransactionController.validateToken();
                            case 2 -> TransactionController.showTokenHistory();
                            case 0 -> System.out.println("Going back...");
                            default -> System.out.println(Colors.yellow + "Not an option." + Colors.reset);
                        }
                    } else
                        System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                }
                case 2 -> {
                    if (activeAdmin.canWrite())
                        TransactionController.createTransaction();
                    else
                        System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                }
                case 3 -> {
                    if (activeAdmin.canRead())
                        TransactionController.showTransactions();
                    else
                        System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                }
                case 0 -> System.out.println("Going back...");
                default -> System.out.println(Colors.yellow + "Not an option." + Colors.reset);
            }
        } while (option != 0);
    }
}
