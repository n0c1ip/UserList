package util;

import java.util.Arrays;
import java.util.EnumSet;


public class ActiveUser {

    private ActiveUser() {
    }

    private static EnumSet<Permission> permissions = null;

    public static void setPermissions(Permission... permissions) {
        ActiveUser.permissions = EnumSet.copyOf(Arrays.asList(permissions));
    }

    public static boolean hasPermission(Permission permission) {
        return permissions != null && permissions.contains(permission);
    }

}