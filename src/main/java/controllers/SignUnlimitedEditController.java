package controllers;

import crudDB.SignUnlimitedService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.SignUnlimited;


public class SignUnlimitedEditController {


    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField nameField;

    private SignUnlimited editedSignUnlimited;
    private boolean invalidData = false;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleOkButton() {

        if(!invalidData){
            editedSignUnlimited.setName(nameField.getText());
            SignUnlimitedService.add(editedSignUnlimited);
            closeWindow();
        }
    }

    @FXML
    private void handleCancelButton() {
       closeWindow();
    }

    public void setEditedSignUnlimited(SignUnlimited editedSignUnlimited) {
        this.editedSignUnlimited = editedSignUnlimited;
        nameField.setText(editedSignUnlimited.getName());
    }

    private void closeWindow(){
        Stage thisWindow = (Stage) okButton.getScene().getWindow();
        thisWindow.close();
    }

}
