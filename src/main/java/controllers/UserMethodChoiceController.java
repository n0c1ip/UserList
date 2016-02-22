package controllers;// Created by mva on 05.02.2016.

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import objects.Location;
import objects.User;

/**
 * Controller of LogIn dialog
 */
public class UserMethodChoiceController {

    @FXML
    private Button newUserButton;
    @FXML
    private Button existingUserButton;

    private Location location;
    private DialogController dialogController;

    @FXML
    private void handleLoginButton(){
        closeWindow();
    }

    public void handleNewUserButton() {
        User user = new User();
        dialogController.showUserEditDialog("Создание пользователя", user);
        closeWindow();
    }

    public void handleExistingUserButton() {
        dialogController.showExistingUserChoiceDialog(location);
        closeWindow();
    }

    public void closeWindow(){
        Stage thisWindow = (Stage) newUserButton.getScene().getWindow();
        thisWindow.close();
    }

    public void setDialogController(DialogController dialogController) {
        this.dialogController = dialogController;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
