package controllers;
//Created by mva on 30.01.2016.

import interfaces.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.User;
import util.ListUtil;

/**
 * <p>UserEditController uses for dialog of editing and adding Users.
 * uses userEdit.fxml for UI.
 * Before initialize need to set up current table, edited user and stage.
 */

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
    @FXML
    private CheckBox firedUser;


    private Stage dialog;
    private String currentTable;
    private User editedUser;


    /**
     * Loading in ComboBox deprtments list.
     */
    @FXML
    private void initialize() {
        ObservableList<String> departmentsList = FXCollections.observableList(ListUtil.getDepartmentsStrings());
        departmentField.setItems(departmentsList);


//        firedUser.selectedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//                System.out.println(newValue);
//            }
//        });



    }

    @Override
    public void setDialog(Stage dialog) {
        this.dialog = dialog;

    }

    /**
     * Handles OK Button, in add or edit dialog
     */
    @FXML
    private void handleOkButton() {

        editedUser.setFirstName(firstNameField.getText());
        editedUser.setLastName(lastNameField.getText());
        editedUser.setMiddleName(middleNameFiel.getText());
        editedUser.setDepartment(departmentField.getValue().toString());
        editedUser.setPosition(positionField.getText());
        editedUser.setLogin(loginField.getText());
        editedUser.setPassword(passwordField.getText());
        editedUser.setMail(mailField.getText());

        if(!IsUserExitstInTable()){
            ListUtil.getListByName(currentTable).add(editedUser);
        }

        this.dialog.close();
    }


    @FXML
    private void handleCancelButton(ActionEvent actionEvent) {
        this.dialog.close();
    }

    /**
     * Set reference to current table which form called this controller
     * @param currentTable
     */
    public void setCurrentTable(String currentTable) {
        this.currentTable = currentTable;
    }


    /**
     * Set edited User
     * @param editedUser
     */
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
        firedUser.setSelected(editedUser.isFired());

    }

    /**
     * Test for existing user in table
     * @return
     */

    private boolean IsUserExitstInTable(){
        return ListUtil.getListByName(currentTable).contains(editedUser);
    }
}
