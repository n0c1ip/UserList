package crud;


import objects.Settings;

import java.io.*;

public class SettingsService {

    private static final String APPLICATION_LOCATION = System.getProperty("user.dir");
    private static final String SETTINGS_FILE_NAME = "settings";
    private static final String SETTINGS_FULL_PATH = APPLICATION_LOCATION + "\\" + SETTINGS_FILE_NAME;


    public static void writeSettings(Settings settings) {
        try {
            FileOutputStream fout = new FileOutputStream(SETTINGS_FULL_PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(settings);
        } catch (FileNotFoundException e) {
            System.out.println(e.getStackTrace());
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public static Settings readSettings() {
        try {
            FileInputStream streamIn = new FileInputStream(SETTINGS_FULL_PATH);
            ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
            Settings readCase = (Settings) objectinputstream.readObject();
            return readCase;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return null;
    }

}
