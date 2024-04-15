package Entities;

public class Admin extends User{
    private boolean superAdmin;

    public Admin(boolean inQuarantine) {
        super(inQuarantine);
        setPermissionsString();
    }

    public Admin(Permissions a, Permissions b, Permissions c) {
        super(a);
        getPermissions()[1] = b;
        getPermissions()[2] = c;
        setPermissionsString();
    }

    public Admin(Permissions a, Permissions b) {
        super(a);
        getPermissions()[1] = b;
        setPermissionsString();
    }

    public Admin(Permissions a) {
        super(a);
        setPermissionsString();
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
    }
}
