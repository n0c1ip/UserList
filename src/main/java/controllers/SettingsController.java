package controllers;// Created by mva on 15.02.2016.

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


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
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    public void handleCancelButton() {
        Stage thisWindow = (Stage) dbTypeComboBox.getScene().getWindow();
        thisWindow.close();
    }
}
