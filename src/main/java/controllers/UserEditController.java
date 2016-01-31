package controllers;
//Created by mva on 30.01.2016.

import interfaces.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.User;
import util.ListUtil;

public class UserEditController implements Dialog {


    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField middleNameFiel;
    @FXML
    private ComboBox departmentField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField mailField;


    private Stage dialog;
    private String currentTable;


    @FXML
    private void initialize() {
        ObservableList<String> departmentsList = FXCollections.observableList(ListUtil.getDepartmentsStrings());
        departmentField.setItems(departmentsList);
    }

    @Override
    public void setDialog(Stage dialog) {
        this.dialog = dialog;
    }

    @FXML
    private void handleOkButton(ActionEvent actionEvent) {
        User user = new User();
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setMiddleName(middleNameFiel.getText());
        user.setDepartment(ListUtil.getDepartmentByName(departmentField.getValue().toString()));
        user.setPosition(positionField.getText());
        user.setLogin(loginField.getText());
        user.setPassword(passwordField.getText());
        user.setMail(mailField.getText());

        ListUtil.getListByName(currentTable).add(user);
        this.dialog.close();
    }

    @FXML
    private void handleCancelButton(ActionEvent actionEvent) {
        this.dialog.close();
    }

    public void setCurrentTable(String currentTable) {
        this.currentTable = currentTable;
    }

}
