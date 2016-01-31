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
    private User editedUser;

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

        editedUser.setFirstName(firstNameField.getText());
        editedUser.setLastName(lastNameField.getText());
        editedUser.setMiddleName(middleNameFiel.getText());
        editedUser.setDepartment(ListUtil.getDepartmentByName(departmentField.getValue().toString()));
        editedUser.setPosition(positionField.getText());
        editedUser.setLogin(loginField.getText());
        editedUser.setPassword(passwordField.getText());
        editedUser.setMail(mailField.getText());

        if(userExitstInTable()){
            ListUtil.getListByName(currentTable).remove(editedUser);
            ListUtil.getListByName(currentTable).add(editedUser);
        }
        else {
            ListUtil.getListByName(currentTable).add(editedUser);
        }
        this.dialog.close();
    }

    @FXML
    private void handleCancelButton(ActionEvent actionEvent) {
        this.dialog.close();
    }

    public void setCurrentTable(String currentTable) {
        this.currentTable = currentTable;
    }

    public void setEditedUser(User editedUser) {
        this.editedUser = editedUser;
        firstNameField.setText(editedUser.getFirstName());
        lastNameField.setText(editedUser.getLastName());
        middleNameFiel.setText(editedUser.getMiddleName());
        departmentField.getSelectionModel().select(editedUser.getDepartment());
        positionField.setText(editedUser.getPosition());
        loginField.setText(editedUser.getLogin());
        passwordField.setText(editedUser.getPassword());
        mailField.setText(editedUser.getMail());
    }

    private boolean userExitstInTable(){
        return ListUtil.getListByName(currentTable).contains(editedUser);
    }
}
