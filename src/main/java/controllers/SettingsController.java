package controllers;// Created by mva on 15.02.2016.

import crudFiles.SettingsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import objects.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;


public class SettingsController {

    private MainController mainController;

    @FXML
    ComboBox<String> dbTypeComboBox;
    @FXML
    TextField serverTextField;
    @FXML
    TextField loginTextField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button testConnectionButton;
    @FXML
    Button saveButton;
    @FXML
    Button cancelButton;

    public SettingsController() {
    }

    @FXML
    private void initialize(){
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "MySQL",
                        "PostgreSQL",
                        "Local DB"
                );
        dbTypeComboBox.setItems(options);

        Optional<Settings> optionalSettings = SettingsService.readSettings();
        if (optionalSettings.isPresent()) {
            Settings settings = optionalSettings.get();
            loginTextField.setText(settings.getUserName());
            passwordField.setText(settings.getPassword());
            serverTextField.setText(settings.getServer());
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void closeWindow() {
        Stage thisWindow = (Stage) dbTypeComboBox.getScene().getWindow();
        thisWindow.close();
    }

    public void handleCancelButton() {
        closeWindow();
    }

    public void handleOkButton() {
        Settings settings = fieldsToSettings();
        if (SettingsService.isSettingsValid(settings)) {
            SettingsService.writeSettings(fieldsToSettings());
            closeWindow();
        } else {
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Проверка соединения", "Соединение не установлено");
        }
    }

    public void handleTestButton() {
        if (SettingsService.isSettingsValid(fieldsToSettings())) {
            DialogController.showAlertDialog(Alert.AlertType.INFORMATION, "Проверка соединения", "Соединение успешно установлено!");
        } else {
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Проверка соединения", "Соединение не установлено");
        }
    }

    private Settings fieldsToSettings() {
        Settings settings = new Settings();
        settings.setUserName(loginTextField.getText());
        settings.setPassword(passwordField.getText());
        settings.setServer(serverTextField.getText());
        return settings;
    }

}
