package UtilityClasses;

import java.util.Scanner;

public class ConsoleReader {
    static Scanner sc = new Scanner(System.in);
    static String input;

    /**Alternative to .nextInt() from Scanner objects. Prevents exceptions.
     * @return An integer that previously was a String.*/
    public static int readInteger() {
        int result = 0;
        boolean goodToGo = false;

        do {
            try {
                System.out.print(">> ");
                input = sc.nextLine();
                result = Integer.parseInt(input);
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
        input = sc.nextLine();
        return input;
    }
}
