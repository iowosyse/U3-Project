package Repositories;

import Project.*;
import UtilityClasses.Colors;
import UtilityClasses.ConsoleReader;

import java.util.ArrayList;

public class UserRepositories {
    public static ArrayList<Client> clients = new ArrayList<>();
    public static  ArrayList<Admin> admins = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<String> usernames = new ArrayList<>();

    public static String validateUsername() {
        boolean isUnique = true;
        String username;

        do {
            System.out.println("Enter your username.");
            username = ConsoleReader.readString();

            for (String usernameToCompare : usernames) {
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
