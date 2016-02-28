package objects;

import org.junit.Assert;
import org.junit.Test;


public class SettingsTest {

    @Test
    public void shouldGetDatabase() {
        Settings.DATABASE expectedDB = Settings.DATABASE.MYSQL;
        Settings settings = new Settings();
        settings.setDatabase(expectedDB);
        Assert.assertEquals(expectedDB, settings.getDatabase());
    }

    @Test
    public void shouldSetServerInnerSettings() {
        String prefix = "jdbc:derby:testdb";
        String postfix = ";create=true";
        String server = "";
        Settings settings = new Settings();
        settings.setDatabase(Settings.DATABASE.EMBEDDED);
        Assert.assertEquals(server, settings.getServer());
        Assert.assertEquals(prefix + server + postfix, settings.getServerWithInnerSettings());
    }

}