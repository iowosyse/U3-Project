package Controllers;

import Controllers.Menus.TitleScreen;
import Entities.Admin;
import Entities.Client;
import Entities.User;
import Repositories.UserRepositories;
import UtilityClasses.*;
import java.util.*;

public class UserControllers {

    public static boolean logIn(){
        User theUser = new User();
        String aux;
        boolean foundUser = false, correctPassword;

        do {
            System.out.println("Enter your username or '---' to go back");
            aux = ConsoleReader.readString();

            for (User toSelect : UserRepositories.users)  {
                if (toSelect.getUsername().equals(aux)){
                    theUser = toSelect;
                    foundUser = true;
                }
            }
            if (!foundUser && !aux.equals("---"))
                System.out.println(Colors.yellow + "User not found" + Colors.reset);
        } while (!foundUser && !aux.equals("---"));

        if (aux.equals("---"))
            return false;

        TitleScreen.activeUser = theUser;
        String passwordToCompare = theUser.getPassword();

        do {
            System.out.println("Enter your password or '---' to go back");
            aux = ConsoleReader.readString();
            aux = HashPassword.hashString(aux);

            if (aux.equals(passwordToCompare)) {
                if (!theUser.isInQuarantine()) //skips the following message if the user is in quarantine
                    System.out.println(Colors.green + "Successful log in!" + Colors.reset);
                else
                    System.out.println(Colors.white + "Your account has been put to quarantine or does not exist. Communicate with an administrator in order to re-activate your account" + Colors.reset);
                correctPassword = true;
                //menus
            } else if(aux.equals(HashPassword.hashString("---"))) {
                System.out.println("Going back...");
                correctPassword = true;
            } else {
                System.out.println(Colors.yellow + "Wrong password." + Colors.reset);
                correctPassword = false;
            }
        } while(!correctPassword && !aux.equals(HashPassword.hashString("---")));

        return !aux.equals(HashPassword.hashString("---"));
    }

    public static void showUsers() {
        int count = 1;

        System.out.printf("| %-3s | %-20s | %-6s | %-6s |%n", "No.", "User´s name", "Birth", "Role");
        System.out.println("------------------------------------------------");

        for (User thisUser : UserRepositories.users) {
            System.out.printf("| %-3s | %-20s | %-6s | %-6s |%n", count, thisUser.getProfile().getName() + " " +
                    thisUser.getProfile().getLastName(), (thisUser.getProfile().getBirthDate().getMonth() + 1) + "-" +
                    thisUser.getProfile().getBirthDate().getYear(), thisUser.getRole());
            System.out.println("------------------------------------------------");
            count ++;
        }
    }

    /**Modifies a selected attribute of a Client*/
    public static void updateUserData(Admin activeAdmin){
        int option;
        User userToChange;
        String aux;

        do {
            UserControllers.showUsers();
            System.out.println("Which user do you want to update their data?");
            System.out.println("Enter 0 to go back");
            System.out.print(">> ");
            option = ConsoleReader.readInteger();

            if (option != 0) {

                userToChange = UserRepositories.users.get(option - 1);

                System.out.println("===============================");
                System.out.println("What do you want to change?");
                System.out.println("1. Name.");
                System.out.println("2. Last name.");
                System.out.println("3. Date of birth.");
                System.out.println("4. Put in quarantine");
                System.out.println("5. Set free from quarantine.");
                System.out.println("0. Go back.");
                System.out.print(">> ");
                option = ConsoleReader.readInteger();
                System.out.println("===============================");

                switch (option) {
                    case 1 -> {
                        System.out.println("Enter the new name.");
                        aux = ConsoleReader.readString();
                        userToChange.getProfile().setName(aux);
                        System.out.println(Colors.green + "Name changed successfully!" + Colors.reset);
                    }
                    case 2 -> {
                        System.out.println("Enter the new last name.");
                        aux = ConsoleReader.readString();
                        userToChange.getProfile().setLastName(aux);
                        System.out.println(Colors.green + "Last name changed successfully!" + Colors.reset);
                    }
                    case 3 -> {
                        System.out.println("Setting new date...");
                        Date newBD = StuffCreator.createDate();

                        userToChange.getProfile().setBirthDate(newBD);
                        System.out.println(Colors.green + "Birthdate changed successfully!" + Colors.reset);
                    }
                    case 4 -> {
                        if (userToChange.isInQuarantine())
                            System.out.println("This user is already in quarantine.");
                        else {
                            if (userToChange instanceof Client) {
                                putInQuarantine(userToChange);
                            } else if (activeAdmin.isSuperAdmin() && userToChange instanceof Admin){
                                //Only admin subClass has the superAdmin attribute, so this converts User theUser to Admin.
                                if (((Admin) userToChange).isSuperAdmin())
                                    System.out.println(Colors.yellow + "Cannot put yourself in quarantine" + Colors.reset);
                                else
                                    putInQuarantine(userToChange);
                            } else
                                System.out.println(Colors.red + "You cannot perform this action." + Colors.reset);
                        }
                    }
                    case 5 -> {
                        if (!userToChange.isInQuarantine())
                            System.out.println("This user is not in quarantine already.");
                        else
                            userToChange.setFreeFromQuarantine();
                    }
                    case 0 -> System.out.println("Going back...");
                    default -> System.out.println(Colors.yellow + "Not an option" + Colors.reset);
                }
            }
        } while (option != 0);
    }

    private static void putInQuarantine(User theUser) {
        theUser.setInQuarantine();
        System.out.println(Colors.green + theUser.getUsername() + " has been put to quarantine successfully!" + Colors.reset);
    }

    public static String validateUsername() {
        boolean isUnique = true;
        String username;

        do {
            System.out.println("Enter your username.");
            username = ConsoleReader.readString();

            for (String usernameToCompare : UserRepositories.usernames) {
                if (username.equals(usernameToCompare)) {
                    isUnique = false;
                    break;
                }
            }

            if (!isUnique)
                System.out.println(Colors.yellow + "That username is already in use." + Colors.reset);
        } while (!isUnique);

        return username;
    }
}
