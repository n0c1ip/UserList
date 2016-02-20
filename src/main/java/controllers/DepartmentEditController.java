package controllers;

import crudDB.DepartmentService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Department;
import objects.Organization;


public class DepartmentEditController {


    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField nameField;

    private Department editedDepartment;
    private Organization activeOrganization;
    private boolean invalidData = false;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleOkButton() {

        if(!invalidData){
            editedDepartment.setName(nameField.getText());
            editedDepartment.setOrganization(activeOrganization);
            DepartmentService.add(editedDepartment);
            closeWindow();
        }
    }

    @FXML
    private void handleCancelButton() {
       closeWindow();
    }

    public void setActiveOrganization(Organization activeOrganization) {
        this.activeOrganization = activeOrganization;
    }

    public void setEditedDepartment(Department editedDepartment) {
        this.editedDepartment = editedDepartment;
        nameField.setText(editedDepartment.getName());
    }

    private void closeWindow(){
        Stage thisWindow = (Stage) okButton.getScene().getWindow();
        thisWindow.close();
    }

}
