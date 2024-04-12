package Project;

import Controllers.Permissions;
import UtilityClasses.Colors;
import UtilityClasses.ConsoleReader;

public class User {
    protected Permissions[] permissions = new Permissions[3];
    protected Profile profile;
    protected String username;
    protected String userType;
    protected String permissionsString = "";
    protected boolean inQuarantine;

    public User (boolean inQuarantine) {
        if (inQuarantine)
            setInQuarantine();
    }

    public User(Permissions a, Permissions b, Permissions c){
        setPermissionsString();
    }

    public User() {
        setPermissionsString();
    }

    public User(Permissions a) {
        getPermissions()[0] = Permissions.READ;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected String password;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getUserType() {
        return userType;
    }

    protected void setPermissionsString() {
        for (int i = 0; i < getPermissions().length; i++) {
            permissionsString += getPermissions()[i] + " ";
        }
    }

    public String getPermissionsString() {
        return permissionsString;
    }

    public boolean canRead() {
        return permissionsString.contains("READ");
    }

    public boolean canWrite() {
        return permissionsString.contains("WRITE");
    }

    public boolean canDelete() {
        return permissionsString.contains("DELETE");
    }

    public boolean canAsk() {
        return permissionsString.contains("ASK");
    }

    public boolean isInQuarantine() {
        return inQuarantine;
    }

    public void setInQuarantine() {
        inQuarantine = true;

        for (int i = 0; i < getPermissions().length; i++) {
            getPermissions()[i] = null;
        }

        setPermissionsString();
    }

    public void setFreeFromQuarantine() {
        int opt;
        String permission;

        inQuarantine = false;

        System.out.println("1. Give specific permissions\n2. Restore all permissions\n0. Go back");
        System.out.println(Colors.yellow + "NOTE: GOING BACK RESTORES ALL PERMISSIONS" + Colors.reset);
        opt = ConsoleReader.readInteger();

        if (opt == 1) {
            System.out.println("Enter all the permissions you want to give to the user");
            permission = ConsoleReader.readString();
            permission = permission.toUpperCase();

            if (permission.contains("READ"))
                getPermissions()[0] = Permissions.READ;

            if (userType.equals("Admin")){
                if (permission.contains("WRITE"))
                    getPermissions()[1] = Permissions.WRITE;

                if (permission.contains("DELETE"))
                    getPermissions()[2] = Permissions.DELETE;
            } else {
                if (permission.contains("ASK"))
                    getPermissions()[1] = Permissions.ASK;
            }
        } else {
            getPermissions()[0] = Permissions.READ;

            if (userType.equals("Admin")) {
                getPermissions()[1] = Permissions.WRITE;
                getPermissions()[2] = Permissions.DELETE;
            } else {
                getPermissions()[1] = Permissions.ASK;
            }
        }

        setPermissionsString();
    }

    public Permissions[] getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions[] permissions) {
        this.permissions = permissions;
    }
}
