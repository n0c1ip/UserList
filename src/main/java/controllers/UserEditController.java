package controllers;
//Created by mva on 30.01.2016.

import crud.DepartmentService;
import crud.UserService;
import interfaces.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Department;
import objects.Location;
import objects.User;

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
    private ComboBox<Department> departmentField;
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
    private Location currentLocation;
    private User editedUser;


    /**
     * Loading in ComboBox deprtments list.
     * It's load when dialog shows, not when creates instance.
     */
    @FXML
    private void initialize() {
        ObservableList<Department> departmentsList = FXCollections.observableArrayList();
        departmentsList.addAll(DepartmentService.getAll());
        departmentField.setItems(departmentsList);
    }

    /**
     * Set reference to Stage. Uses when dialog closes.
     * @param dialog
     */
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
        editedUser.setDepartment(departmentField.getValue());
        editedUser.setPosition(positionField.getText());
        editedUser.setLogin(loginField.getText());
        editedUser.setPassword(passwordField.getText());
        editedUser.setMail(mailField.getText());

        if(!isUserAlreadyExist()){
            UserService.add(editedUser);
        }

        this.dialog.close();
    }


    /**
     * Handle cancel Button pressed. Closes dialog.
     */
    @FXML
    private void handleCancelButton() {
        this.dialog.close();
    }

    /**
     * Set reference to current table which from called this controller
     * @param currentLocation
     */
    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
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

    private boolean isUserAlreadyExist(){
        //TODO check from db
        return false;
    }
}
