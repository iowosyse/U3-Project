package UtilityClasses;

import Controllers.Menus.TitleScreen;

public class EasterEggs {
    public static void findThem() {
        if (TitleScreen.counter == 20) {
            System.out.println("Fine, I'll just end myself.");
        } else if (TitleScreen.counter == 17) {
            System.out.println("MEOW? (waiting for something to happen?)");// lil easter egg:3 -3
            TitleScreen.counter++;
        } else if (TitleScreen.counter >= 10) {
            System.out.println("Nope, there's nothing over here.");
            TitleScreen.counter++;
        } else {
            System.out.println("Not an option");
            TitleScreen.counter++;
        }
    }
}
