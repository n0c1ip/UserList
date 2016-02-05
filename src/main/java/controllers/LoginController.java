package controllers;// Created by mva on 05.02.2016.


import interfaces.Dialog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller of LogIn dialog
 */
public class LoginController implements Dialog{

    @FXML
    private Button loginButton;
    private Stage dialog;

    @FXML
    private void handleLoginButton(){
        dialog.close();
    }


    @Override
    public void setDialog(Stage dialog) {
        this.dialog = dialog;
    }
}
