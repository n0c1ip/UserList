package controllers;

import crudDB.BeanValidation;
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
    private TextField nameField;

    private SignUnlimited editedSignUnlimited;

    @FXML
    private void handleOkButton() {
        editedSignUnlimited.setName(nameField.getText());
        if (BeanValidation.isCorrectData(editedSignUnlimited)) {
            SignUnlimitedService.add(editedSignUnlimited);
            closeWindow();
        } else {
            DialogController.showErrorDialog(BeanValidation.getViolationsText(editedSignUnlimited));
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
