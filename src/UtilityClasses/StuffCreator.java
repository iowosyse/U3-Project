package UtilityClasses;

import Controllers.HashPassword;
import Project.Profile;
import Project.User;
import java.util.*;

public class StuffCreator {
    public static Profile createProfile() {
        Date dateOfBirth;
        String aux1, aux2;

        System.out.println("What's the name?");
        aux1 = ConsoleReader.readString();

        System.out.println("What's the last name?");
        aux2 = ConsoleReader.readString();

        System.out.println("When's their birthday?"); //lego batman 2 goty 2024
        dateOfBirth = createDate();

        return new Profile(aux1, aux2, dateOfBirth);
    }

    public static Date createDate() {
        Date newDate = new Date();
        int aux;

        System.out.print("\tDay ");
        aux = ConsoleReader.readInteger();
        newDate.setDate(aux);

        System.out.print("\tMonth(numeric) ");
        aux = ConsoleReader.readInteger();
        newDate.setMonth(aux - 1);

        System.out.print("\tYear ");
        aux = ConsoleReader.readInteger();
        newDate.setYear(aux);

        return newDate;
    }
}
