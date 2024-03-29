package Controllers.Menus;

import Controllers.UserControllers;
import Project.*;
import UtilityClasses.Colors;
import UtilityClasses.ConsoleReader;

import java.util.*;

public class TitleScreen {
    public static int counter = 1;
    public static User activeUser;

    /**First thing to show up*/
    public static void titleScreen() {
        int key;
        boolean loggedIn;

        do {
            System.out.println("------This is a title screen--------");
            System.out.println("===============================");
            System.out.println("1. Log in");
            System.out.println("0. Exit");
            key = ConsoleReader.readInteger();
            System.out.println("===============================");
            switch (key) {
                case 1 -> {
                    loggedIn = UserControllers.logIn();

                    if (loggedIn) {
                        if (!activeUser.isInQuarantine()) {
                            if (activeUser.getUserType().equals("Admin")){
                                Admin activeAdmin = (Admin) activeUser;
                                AdminMenus.adminMainMenu(activeAdmin);
                            } else {
                                Client activeClient = (Client) activeUser;
                                ClientMenus.clientMainMenu(activeClient);
                            }
                        }
                    }
                }
                case 0 -> System.out.println("Goodbye!");
                case -1 ->
                    System.out.println("Yahaha! You found me!");// easter egg -2
                default -> {
                    UtilityClasses.EasterEggs.findThem();
                    if (counter == 20) {
                        key = 0;
                    }
                }
            }
        } while (key != 0);
    }
}
