package controllers;

import crudDB.BeanValidation;
import crudDB.SignUnlimitedService;
import crudDB.UserSignUnlimitedService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.SignUnlimited;
import objects.UserSignUnlimited;


public class UserSignUnlimitedEditController {

    @FXML
    private Button okButton;
    @FXML
    private ComboBox<SignUnlimited> signBox;
    @FXML
    private TextField valueField;

    private UserSignUnlimited editedUserSignUnlimited;

    @FXML
    private void initialize() {
        ObservableList<SignUnlimited> signUnlimitedList = FXCollections.observableArrayList();
        signUnlimitedList.addAll(SignUnlimitedService.getAll());
        signBox.setItems(signUnlimitedList);
    }

    @FXML
    private void handleOkButton() {
        editedUserSignUnlimited.setSignUnlimited(signBox.getValue());
        editedUserSignUnlimited.setValue(valueField.getText());
        if (BeanValidation.isCorrectData(editedUserSignUnlimited)) {
        UserSignUnlimitedService.add(editedUserSignUnlimited);
        closeWindow();
        } else {
            DialogController.showErrorDialog(BeanValidation.getViolationsText(editedUserSignUnlimited));
        }
    }

    @FXML
    private void handleCancelButton() {
       closeWindow();
    }

    public void setEditedUserSignUnlimited(UserSignUnlimited editedUserSignUnlimited) {
        this.editedUserSignUnlimited = editedUserSignUnlimited;
        signBox.setValue(editedUserSignUnlimited.getSignUnlimited());
        valueField.setText(editedUserSignUnlimited.getValue());
    }

    private void closeWindow(){
        Stage thisWindow = (Stage) okButton.getScene().getWindow();
        thisWindow.close();
    }

}
