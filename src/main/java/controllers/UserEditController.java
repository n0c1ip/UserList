package controllers;

import crudDB.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.*;
import util.I18n;

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
    private ComboBox<Pc> pcComboBox;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField mailField;
    @FXML
    private Button pcButton;

    private User editedUser;
    private DialogController dialogController;

    public void setDialogController(DialogController dialogController) {
        this.dialogController = dialogController;
    }

    /**
     * Loading in ComboBox departments list.
     * It's load when dialog shows, not when creates instance.
     */
    @FXML
    private void initialize() {
        //Set ComboBoxes
        organizationComboBox.setItems(FXCollections.observableArrayList(OrganizationService.getAll()));
        departmentBox.setItems(FXCollections.observableArrayList(DepartmentService.getAll()));
        locationBox.setItems(FXCollections.observableArrayList(LocationService.getAll()));
        pcComboBox.setItems(FXCollections.observableArrayList(PcService.getAll()).sorted());

        //Change Departments in departmentsComboBox when Organization changes
        organizationComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable1, oldValue, newValue ) -> setDepartmentBoxByOrganization(newValue));
    }

    /**
     * Handles OK Button, in add or edit dialog
     */
    @FXML
    private void handleOkButton() {
        editedUser.setFirstName(firstNameField.getText());
        editedUser.setLastName(lastNameField.getText());
        editedUser.setMiddleName(middleNameField.getText());
        editedUser.setLocation(locationBox.getValue());
        editedUser.setDepartment(departmentBox.getValue());
        editedUser.setPosition(positionField.getText());
        editedUser.setLogin(loginField.getText());
        editedUser.setPassword(passwordField.getText());
        editedUser.setMail(mailField.getText());
        if (pcComboBox.getValue() != null) {
            if(pcComboBox.getValue().getUser() != null){
                User oldUser = pcComboBox.getValue().getUser();
                oldUser.setPc(null);
                UserService.update(oldUser);
            }
            editedUser.setPc(pcComboBox.getValue());
        }
        if (BeanValidation.isCorrectData(editedUser)) {
            UserService.add(editedUser);
            closeWindow();
        } else {
            DialogController.showErrorDialog(BeanValidation.getViolationsText(editedUser));
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
     */
    public void setEditedUser(User editedUser) {
        this.editedUser = editedUser;
        firstNameField.setText(editedUser.getFirstName());
        lastNameField.setText(editedUser.getLastName());
        middleNameField.setText(editedUser.getMiddleName());
        if (editedUser.getDepartment() != null) {
            organizationComboBox.getSelectionModel().select(editedUser.getDepartment().getOrganization());
        }
        locationBox.getSelectionModel().select(editedUser.getLocation());
        departmentBox.getSelectionModel().select(editedUser.getDepartment());
        positionField.setText(editedUser.getPosition());
        if(editedUser.getPc() != null){
            pcComboBox.getSelectionModel().select(editedUser.getPc());
        }
        loginField.setText(editedUser.getLogin());
        passwordField.setText(editedUser.getPassword());
        mailField.setText(editedUser.getMail());
    }

    @FXML
    private void handleUserSignUnlimitedButton() {
        dialogController.showUserSignUnlimitedTableDialog(I18n.DIALOG.getString("UserSign.All"), editedUser);
    }

    public void setDepartmentBoxByOrganization(Organization organization){
        departmentBox.valueProperty().set(null);
        ObservableList<Department> departmentList = FXCollections.observableArrayList();
        departmentList.setAll(DepartmentService.getByOrganization(organization));
        if(departmentList.isEmpty()){
            departmentBox.setDisable(true);
        } else {
            departmentBox.setDisable(false);
        }
        departmentBox.setItems(departmentList);
    }

    @FXML
    private void handleShowPcButton(){
        if(pcComboBox.getValue() != null){
           dialogController.showPcEditDialog("Редактирование компьютера", pcComboBox.getValue());
        }
    }

    private void closeWindow(){
        Stage thisWindow = (Stage) okButton.getScene().getWindow();
        thisWindow.close();
    }

}
