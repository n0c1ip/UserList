package controllers;// Created by mva on 15.02.2016.

import crudDB.EntityManagerFactory;
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
import java.util.ArrayList;
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

    boolean connectionElementsDisabled = false;
    private TabPane tabLayout;

    public SettingsController() {
    }

    private final String MYSQL_DB_NAME = "MySQL";
    private final String EMBEDDED_DB_NAME = "Embedded DB";

    @FXML
    private void initialize(){
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        MYSQL_DB_NAME,
                        EMBEDDED_DB_NAME
                );
        dbTypeComboBox.setItems(options);

        //Disables/enables connection settings fields
        dbTypeComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable1, oldValue, newValue ) -> {
                    if(newValue.matches(EMBEDDED_DB_NAME)){
                        serverTextField.setDisable(true);
                        loginTextField.setDisable(true);
                        passwordField.setDisable(true);
                        //testConnectionButton.setDisable(true);
                        connectionElementsDisabled = true;
                    } else {
                        if(connectionElementsDisabled){
                            serverTextField.setDisable(false);
                            loginTextField.setDisable(false);
                            passwordField.setDisable(false);
                          //  testConnectionButton.setDisable(false);
                            connectionElementsDisabled = false;
                        }
                    }
                });

        Optional<Settings> optionalSettings = SettingsService.readSettings();
        if (optionalSettings.isPresent()) {
            Settings settings = optionalSettings.get();
            switch (settings.getDatabase()) {
                case MYSQL:
                    dbTypeComboBox.setValue(MYSQL_DB_NAME);
                    break;
                case EMBEDDED:
                    dbTypeComboBox.setValue(EMBEDDED_DB_NAME);
                    break;
            }
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
            if (tabLayout != null) {
                tabLayout.getTabs().removeAll(tabLayout.getTabs());
            }
            EntityManagerFactory.closeEntityManagerFactory();
            EntityManagerFactory.initialize();
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
        switch (dbTypeComboBox.getValue()) {
            case MYSQL_DB_NAME:
                settings.setDatabase(Settings.DATABASE.MYSQL);
                settings.setUserName(loginTextField.getText());
                settings.setPassword(passwordField.getText());
                settings.setServer(serverTextField.getText());
                break;
            case EMBEDDED_DB_NAME:
                settings.setDatabase(Settings.DATABASE.EMBEDDED);
                break;
        }
        return settings;
    }

    public void setTabLayout(TabPane tabLayout) {
        this.tabLayout = tabLayout;
    }
}
