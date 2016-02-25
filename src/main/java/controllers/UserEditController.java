package controllers;
//Created by mva on 30.01.2016.

import crudDB.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.*;

/**
 * <p>UserEditController uses for dialog of editing and adding Users.
 * uses userEditDialog.fxml for UI.
 * Before initialize need to set up current table, edited user and stage.
 */

public class UserEditController {


    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField middleNameField;
    @FXML
    private ComboBox<Organization> organizationComboBox;
    @FXML
    private ComboBox<Location> locationBox;
    @FXML
    private ComboBox<Department> departmentBox;
    @FXML
    private TextField positionField;
    @FXML
    private TextField pcField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField mailField;

    private User editedUser;
    private boolean invalidData = false;
    private DialogController dialogController;

    public void setDialogController(DialogController dialogController) {
        this.dialogController = dialogController;
    }

    /**
     * Loading in ComboBox deprtments list.
     * It's load when dialog shows, not when creates instance.
     */
    @FXML
    private void initialize() {

        //Load organizationComboBox
        ObservableList<Organization> organizationList = FXCollections.observableArrayList();
        organizationList.addAll(OrganizationService.getAll());
        organizationComboBox.setItems(organizationList.sorted());

        //Load departments to ComboBox
        ObservableList<Department> departmentsList = FXCollections.observableArrayList();
        departmentsList.addAll(DepartmentService.getAll());
        departmentBox.setItems(departmentsList.sorted());

        //Load locations to ComboBox
        ObservableList<Location> locationsList = FXCollections.observableArrayList();
        locationsList.addAll(LocationService.getAll());
        locationBox.setItems(locationsList);

        //Change Departments in departmentsComboBox when Organization changes
        organizationComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable1, oldValue, newValue ) -> setDepartmentBoxByOrganization(newValue));

    }

    /**
     * Handles OK Button, in add or edit dialog
     */
    @FXML
    private void handleOkButton() {

        if(!invalidData && departmentBox.getSelectionModel().getSelectedItem() != null){
            editedUser.setFirstName(firstNameField.getText());
            editedUser.setLastName(lastNameField.getText());
            editedUser.setMiddleName(middleNameField.getText());
            editedUser.setLocation(locationBox.getValue());
            editedUser.setDepartment(departmentBox.getValue());
            editedUser.setPosition(positionField.getText());
            editedUser.setPc(PcService.add(new Pc(pcField.getText())));
            editedUser.setLogin(loginField.getText());
            editedUser.setPassword(passwordField.getText());
            editedUser.setMail(mailField.getText());
            UserService.add(editedUser);
            closeWindow();
        } else {
            //show dialog invalid data
        }
    }

    /**
     * Handle cancel Button pressed. Closes dialog.
     */
    @FXML
    private void handleCancelButton() {
       closeWindow();
    }

    /**
     * Set edited User
     * @param editedUser
     */
    public void setEditedUser(User editedUser) {
        this.editedUser = editedUser;
        firstNameField.setText(editedUser.getFirstName());
        lastNameField.setText(editedUser.getLastName());
        middleNameField.setText(editedUser.getMiddleName());
        organizationComboBox.getSelectionModel().select(editedUser.getDepartment().getOrganization());
        locationBox.getSelectionModel().select(editedUser.getLocation());
        departmentBox.getSelectionModel().select(editedUser.getDepartment());
        positionField.setText(editedUser.getPosition());
        if(editedUser.getPc() != null){
            pcField.setText(editedUser.getPc().getName());
        }
        loginField.setText(editedUser.getLogin());
        passwordField.setText(editedUser.getPassword());
        mailField.setText(editedUser.getMail());

    }


    @FXML
    private void handleUserSignUnlimitedButton() {
        dialogController.showUserSignUnlimitedTableDialog("Признаки пользователя", editedUser);
    }



    public void setDepartmentBoxByOrganization(Organization organization){
        departmentBox.valueProperty().set(null);
        ObservableList<Department> departmentList = FXCollections.observableArrayList();
        departmentList.setAll(DepartmentService.getByOrganization(organization));
        if(departmentList.isEmpty()){
            departmentBox.setDisable(true);
            invalidData = true;
        } else {
            departmentBox.setDisable(false);
            invalidData = false;
        }
        departmentBox.setItems(departmentList);

    }

    private void closeWindow(){
        Stage thisWindow = (Stage) okButton.getScene().getWindow();
        thisWindow.close();
    }

}
