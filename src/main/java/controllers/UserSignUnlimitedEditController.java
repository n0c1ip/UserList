package controllers;

import crudDB.DepartmentService;
import crudDB.SignUnlimitedService;
import crudDB.UserSignUnlimitedService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Department;
import objects.SignUnlimited;
import objects.UserSignUnlimited;


public class UserSignUnlimitedEditController {


    private MainController mainController;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<SignUnlimited> signBox;
    @FXML
    private TextField valueField;

    private UserSignUnlimited editedUserSignUnlimited;
    private boolean invalidData = false;

    @FXML
    private void initialize() {
        ObservableList<SignUnlimited> signUnlimitedList = FXCollections.observableArrayList();
        signUnlimitedList.addAll(SignUnlimitedService.getAll());
        signBox.setItems(signUnlimitedList);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleOkButton() {
        if(signBox.getValue() != null && valueField.getText() != null){
            editedUserSignUnlimited.setSignUnlimited(signBox.getValue());
            editedUserSignUnlimited.setValue(valueField.getText());
            UserSignUnlimitedService.add(editedUserSignUnlimited);
            closeWindow();
        } else {
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Сохранение признака", "Не все обязательные поля заполнены");
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
