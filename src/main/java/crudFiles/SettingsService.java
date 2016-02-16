package crudFiles;


import controllers.DialogController;
import javafx.scene.control.Alert;
import objects.Settings;

import java.io.*;
import java.util.Optional;

public class SettingsService {

    private static final String APPLICATION_LOCATION = System.getProperty("user.dir");
    private static final String DEFAULT_FILE_NAME = "settings";
    private static final String DEFAULT_FULL_PATH = APPLICATION_LOCATION + "\\" + DEFAULT_FILE_NAME;


    public static void writeSettings(Settings settings) {
        writeSettings(settings, "");
    }

    public static void writeSettings(Settings settings, String fullPath) {
        String actualFullPath = (fullPath != "" ? fullPath : DEFAULT_FULL_PATH);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(actualFullPath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(settings);
            objectOutputStream.close();
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
        String actualFullPath = (fullPath != "" ? fullPath : DEFAULT_FULL_PATH);
        try {
            FileInputStream fileInputStream = new FileInputStream(actualFullPath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Settings settings = (Settings) objectInputStream.readObject();
            objectInputStream.close();
            return Optional.of(settings);
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

}
