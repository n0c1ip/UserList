package util;


import org.junit.Assert;
import org.junit.Test;


public class ActiveUserTest {

    @Test
    public void shouldSetPermissions(){
        ActiveUser.setPermissions(Permission.READ);
        Assert.assertTrue(ActiveUser.hasPermission(Permission.READ));
        Assert.assertFalse(ActiveUser.hasPermission(Permission.WRITE));

        ActiveUser.setPermissions(Permission.WRITE, Permission.READ);
        Assert.assertTrue(ActiveUser.hasPermission(Permission.READ));
        Assert.assertTrue(ActiveUser.hasPermission(Permission.WRITE));
    }


}
