package Project;

import Controllers.Permissions;
import UtilityClasses.Colors;
import UtilityClasses.ConsoleReader;

public class User {
    public Permissions[] permissions = new Permissions[3];
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
        permissions[0] = Permissions.READ;
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
        for (int i = 0; i < permissions.length; i++) {
            permissionsString += permissions[i] + " ";
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

        for (int i = 0; i < permissions.length; i++) {
            permissions[i] = null;
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
                permissions[0] = Permissions.READ;

            if (userType.equals("Admin")){
                if (permission.contains("WRITE"))
                    permissions[1] = Permissions.WRITE;

                if (permission.contains("DELETE"))
                    permissions[2] = Permissions.DELETE;
            } else {
                if (permission.contains("ASK"))
                    permissions[1] = Permissions.ASK;
            }
        } else {
            permissions[0] = Permissions.READ;

            if (userType.equals("Admin")) {
                permissions[1] = Permissions.WRITE;
                permissions[2] = Permissions.DELETE;
            } else {
                permissions[1] = Permissions.ASK;
            }
        }

        setPermissionsString();
    }
}
