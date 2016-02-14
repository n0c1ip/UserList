package controllers;// Created by mva on 05.02.2016.

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller of LogIn dialog
 */
public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private void handleLoginButton(){
        closeWindow();
    }


    public void closeWindow(){
        Stage thisWindow = (Stage) loginButton.getScene().getWindow();
        thisWindow.close();
    }
}
