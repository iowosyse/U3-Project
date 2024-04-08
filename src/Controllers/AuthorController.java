package Controllers;

import java.util.*;
import Project.*;
import Repositories.*;
import UtilityClasses.*;

public class AuthorController {
    public static void createAuthor() {
        Author newAuthor = new Author();

        newAuthor.setProfile(StuffCreator.createProfile()); //makes easier the creation of a profile
        ProfileRepositories.profiles.add(newAuthor.getProfile());
        AuthorRepositories.authors.add(newAuthor);

        System.out.println(Colors.green + "Author created successfully!" + Colors.reset);
    }

    /**Shows all authors*/
    public static void showAuthors(boolean showBooks) { //should be in repositories or aux methods
        int count = 1;

        System.out.printf("| %-3s | %-20s | %-6s |%n", "No.", "Author name", "Birth");
        System.out.println("---------------------------------------"); //just showing stuff

        for (Author thisAuthor : AuthorRepositories.authors) {
            System.out.printf("| %-3s | %-20s | %-6s |%n", count, thisAuthor.getProfile().getName() + " " +
                    thisAuthor.getProfile().getLastName(), (thisAuthor.getProfile().getBirthDate().getMonth() + 1) + "-" +
                    thisAuthor.getProfile().getBirthDate().getYear());

            if (showBooks && thisAuthor.wroteBooks()) {
                System.out.printf("| %-26s |%n", "Books by " + thisAuthor.getProfile().getName() +
                        " " + thisAuthor.getProfile().getLastName() + ": ");
                for (Book bok : thisAuthor.writtenBooks) {
                    System.out.printf("| %-26s |%n", bok.getTitle());
                }
                System.out.println("---------------------------------------");
            }
            count ++;
        }
    }

    /**Modifies a selected attribute of the author object*/
    public static void updateAuthorData() {
        int option;
        Author toChange;
        String aux;

        do {
            showAuthors(false);
            do {
                System.out.println("Which author do you want to update their data?");
                System.out.println("Enter 0 to go back");
                option = ConsoleReader.readInteger();
            } while (option - 1 > AuthorRepositories.authors.size() || option < 0);

            if (option != 0) {
                toChange = AuthorRepositories.authors.get(option - 1);

                System.out.println("===============================");
                System.out.println(Colors.blue + "---If you want to change the books of an author please do it in the 'book menu'.---" + Colors.reset);
                System.out.println("What do you want to change?");
                System.out.println("1. Name.");
                System.out.println("2. Last name.");
                System.out.println("3. Date of birth.");
                System.out.println("0. Go back.");
                option = ConsoleReader.readInteger();
                System.out.println("===============================");

                switch (option) {
                    case 1 -> {
                        System.out.println("Enter the new name.");
                        aux = ConsoleReader.readString();
                        toChange.getProfile().setName(aux);
                    }
                    case 2 -> {
                        System.out.println("Enter the new last name.");
                        aux = ConsoleReader.readString();
                        toChange.getProfile().setLastName(aux);
                    }
                    case 3 -> {
                        System.out.println("Setting new date...");
                        Date newBD = StuffCreator.createDate();
                        toChange.getProfile().setBirthDate(newBD);
                    }
                    case 0 -> System.out.println("Going back...");
                    default -> System.out.println(Colors.yellow + "Not an option." + Colors.reset);
                }
            }
        } while (option != 0);
    }

    /**Deletes the selected author of the authors ArrayList. Cannot delete someone if they still have books*/
    public static void deleteAuthor() {
        int option;
        Author toDelete;
        System.out.println(Colors.blue + "---You must delete every book from an author in order to delete the author themselves---" + Colors.reset);

        showAuthors(false);

        do {
            System.out.println("Which author do you want to delete?");
            option = ConsoleReader.readInteger();
        } while (option - 1 > AuthorRepositories.authors.size() || option < 0);

        if (option != 0) {
            toDelete = AuthorRepositories.authors.get(option - 1);

            if (toDelete.wroteBooks()) { //verifies if can delete
                System.out.println(Colors.yellow + "This author cannot be deleted since they still have books." + Colors.reset);
                System.out.println(Colors.yellow + "Remove all of their books in the 'Book menu' with the 'Delete a book' option." + Colors.reset);
            } else {
                AuthorRepositories.authors.remove(toDelete);
                System.out.println(Colors.green + "Author deleted successfully!" + Colors.reset);
            }
        } else
            System.out.println("Going back...");
    }
}
