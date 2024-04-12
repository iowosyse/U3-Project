package UtilityClasses;

import java.util.Scanner;

public class ConsoleReader {
    static Scanner sc = new Scanner(System.in);
    static String input;

    public static int readInteger() {
        int result = 0;
        boolean goodToGo = false;

        do {
            try {
                System.out.print(">> ");
                input = sc.nextLine();
                result = Integer.parseInt(input); //Here's where the exception pops up.
                goodToGo = true;
            } catch (Exception e) {
                //Somehow the System.err.println() gives problems and makes it hard to align correctly with the other prints.
                System.out.println(Colors.red + "-- Invalid input --" + Colors.reset);
            }
        } while (!goodToGo);

        return result;
    }

    public static double readDouble() {
        double result = 0;
        boolean goodToGo = false;

        do {
            try {
                System.out.print(">> ");
                input = sc.nextLine();
                result = Double.parseDouble(input); //Here's where the exception pops up.
                goodToGo = true;
            } catch (Exception e) {
                //Somehow the System.err.println() gives problems and makes it hard to align correctly with the other prints.
                System.out.println(Colors.red + "-- Invalid input --" + Colors.reset);
            }
        } while (!goodToGo);

        return result;
    }

    public static boolean readBoolean() {
        boolean result = false;
        boolean goodToGo = false;

        do {
            try {
                System.out.print(">> ");
                input = sc.nextLine();
                result = Boolean.parseBoolean(input); //Here's where the exception pops up.
                goodToGo = true;
            } catch (Exception e) {
                //Somehow the System.err.println() gives problems and makes it hard to align correctly with the other prints.
                System.out.println(Colors.red + "-- Invalid input --" + Colors.reset);
            }
        } while (!goodToGo);

        return result;
    }

    /**@return The string the user typed*/
    public static String readString() {
        System.out.print(">> ");
        return sc.nextLine();
    }
}
