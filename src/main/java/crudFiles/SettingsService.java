package crudFiles;


import controllers.DialogController;
import javafx.scene.control.Alert;
import objects.Settings;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class SettingsService {

    private SettingsService() {}

    private static final String APPLICATION_LOCATION = System.getProperty("user.dir");
    private static final String DEFAULT_FILE_NAME = "settings";
    private static final String DEFAULT_FULL_PATH = APPLICATION_LOCATION + File.separator + DEFAULT_FILE_NAME;

    public static void writeSettings(Settings settings) {
        writeSettings(settings, "");
    }

    public static void writeSettings(Settings settings, String fullPath) {
        String actualFullPath = (fullPath.equals("") ? DEFAULT_FULL_PATH : fullPath);
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(actualFullPath);
            try(ObjectOutput objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(settings);
                objectOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось сохранить данные в файл настроек (не найден класс)");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось сохранить данные в файл настроек (ошибка записи)");
        }
    }

    public static Optional<Settings> readSettings() {
        return readSettings("");
    }

    public static Optional<Settings> readSettings(String fullPath) {
        String actualFullPath = (fullPath.equals("") ? DEFAULT_FULL_PATH : fullPath);
        try (FileInputStream fileInputStream = new FileInputStream(actualFullPath)) {
            try (ObjectInput objectInputStream = new ObjectInputStream(fileInputStream)) {
                Settings settings = (Settings) objectInputStream.readObject();
                objectInputStream.close();
                return Optional.of(settings);
            }
        } catch (FileNotFoundException e) {
            // totally ok
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить данные из файла настроек (не найден класс)");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить данные из файла настроек (ошибка чтения)");
        }
        return Optional.empty();
    }

    public static boolean isSettingsValid(Settings settings) {
        try (Connection ignored = DriverManager.getConnection(settings.getServerWithInnerSettings(), settings.getUserName(), settings.getPassword())) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
