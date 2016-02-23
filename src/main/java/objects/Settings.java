package objects;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static objects.Settings.DATABASE.MYSQL;


interface DriverInnerSettings {
    default String getServerPrefix() {return "";}
    default String getServerPostfix() {return "";}
    default String getDriverClass() {return "";}
}

class MysqlDbSettings implements DriverInnerSettings, Serializable{
    @Override
    public String getServerPrefix() {
        return "jdbc:mysql://";
    }
    @Override
    public String getDriverClass() {
        return "com.mysql.jdbc.Driver";
    }
}

class EmbeddedDbSettings implements DriverInnerSettings, Serializable{
    @Override
    public String getServerPrefix() {
        return "jdbc:derby:testdb";
    }
    @Override
    public String getServerPostfix() {
        return ";create=true";
    }
    @Override
    public String getDriverClass() {
        return "org.apache.derby.jdbc.EmbeddedDriver";
    }
}

public class Settings implements Serializable{

    public enum LANGUAGE {ENGLISH, RUSSIAN};
    private static final Map<LANGUAGE, Locale> languageSettings;

    public enum DATABASE {MYSQL, EMBEDDED};
    private static final Map<DATABASE, DriverInnerSettings> databaseSettings;

    static {
        databaseSettings = new HashMap<>();
        databaseSettings.put(DATABASE.MYSQL, new MysqlDbSettings());
        databaseSettings.put(DATABASE.EMBEDDED, new EmbeddedDbSettings());

        languageSettings = new HashMap<>();
        languageSettings.put(LANGUAGE.ENGLISH, new Locale("en"));
        languageSettings.put(LANGUAGE.RUSSIAN, new Locale("ru"));

    }


    LANGUAGE language;
    public void setLanguage(LANGUAGE language) {
        this.language = language;
    }
    public LANGUAGE getLanguage() {
        return language;
    }
    public Locale getLanguageLocale() {
        return languageSettings.get(language);
    }

    DriverInnerSettings driverInnerSettings;
    DATABASE database;
    public void setDatabase(DATABASE database) {
        driverInnerSettings = databaseSettings.get(database);
        this.database = database;
    }
    public DATABASE getDatabase() {
        return database;
    }

    private String userName = "";
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }


    private String password = "";
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }


    private String server = "";
    public void setServer(String server) {
        this.server = server;
    }
    public String getServer() {
        return server;
    }
    public String getServerWithInnerSettings() {
        return driverInnerSettings.getServerPrefix() + server + driverInnerSettings.getServerPostfix();
    }

    public String getDriverClass() {
        return driverInnerSettings.getDriverClass();
    }


}
