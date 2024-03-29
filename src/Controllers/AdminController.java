package Controllers;

import Project.Admin;
import Repositories.ProfileRepositories;
import Repositories.UserRepositories;
import UtilityClasses.*;

/**Shows every admin*/
public class AdminController {
    public static void createAdmin() {
        int opt;

        System.out.println("----------------------------");
        System.out.println("1. Create admin with all permissions");
        System.out.println("2. Create admin without delete permission. Still can read and write");
        System.out.println("3. Create an only-read admin.");
        System.out.println("0. Go back...");
        System.out.println("----------------------------");
        opt = ConsoleReader.readInteger();

        switch (opt) {
            case 1 -> {
                Admin newAdmin = new Admin(Permissions.READ, Permissions.WRITE, Permissions.DELETE);
                setNewAdminProfile(newAdmin);
            }
            case 2 -> {
                Admin newAdmin = new Admin(Permissions.READ, Permissions.WRITE);
                setNewAdminProfile(newAdmin);
            }
            case 3 -> {
                Admin newAdmin = new Admin(Permissions.READ);
                setNewAdminProfile(newAdmin);
            }
            case 0 -> System.out.println("Going back...");
            default -> System.out.println(Colors.yellow + "Not an option." + Colors.reset);
        }
    }

    public static void showAdmins() {
        int count = 1;

        System.out.printf("| %-3s | %-20s | %-6s |%n", "No.", "Admin name", "Birth");
        System.out.println("---------------------------------------");

        for (Admin thisAdmin : UserRepositories.admins) {
            System.out.printf("| %-3s | %-20s | %-6s |%n", count, thisAdmin.getProfile().getName() + " " +
                    thisAdmin.getProfile().getLastName(), (thisAdmin.getProfile().getBirthDate().getMonth() + 1) + "-" +
                    thisAdmin.getProfile().getBirthDate().getYear());
            System.out.println("---------------------------------------");
            count ++;
        }
    }

    /**Deletes an admin from the admins ArrayList*/
    public static void deleteAdmin(Admin theAdmin) {
        int opt;
        Admin toDelete;

        //Even though the super admin has all the permissions, I need to use the getter for the permissions.
        if (theAdmin.isSuperAdmin() && theAdmin.getPermissionsString().contains("DELETE")){
            showAdmins();
            //Makes sure it's a valid index
            do {
                System.out.println("What admin do you want to delete?");
                System.out.println("Enter 0 to go back.");
                opt = ConsoleReader.readInteger();
                toDelete = UserRepositories.admins.get(opt - 1);
            } while ((opt - 1) > UserRepositories.admins.size() || opt < 0);

            if (opt != 0) {
                if (toDelete == theAdmin)
                    System.out.println(Colors.yellow + "Cannot delete a super admin. Must make a new super admin before deleting " +
                            theAdmin.getProfile().getName() + " " + theAdmin.getProfile().getLastName() + Colors.reset);
                else {
                    UserRepositories.admins.remove(toDelete);
                    UserRepositories.users.remove(toDelete);
                    System.out.println(Colors.green + "Admin removed successfully!" + Colors.reset);
                }
            }
        } else
            System.out.println(Colors.yellow + "You cannot perform this action" + Colors.reset);
    }

    private static void setNewAdminProfile(Admin newAdmin) {
        String aux;
        newAdmin.setProfile(StuffCreator.createProfile());

        System.out.println("Create a username.");
        newAdmin.setUsername(UserRepositories.validateUsername());

        System.out.println("Create a password.");
        aux = ConsoleReader.readString();
        newAdmin.setPassword(HashPassword.hashString(aux));

        UserRepositories.admins.add(newAdmin);
        UserRepositories.users.add(newAdmin);

        ProfileRepositories.profiles.add(newAdmin.getProfile());

        System.out.println(Colors.green + "Admin created successfully" + Colors.reset);
    }
}
