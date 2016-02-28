package controllers;

import crudDB.OrganizationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Organization;


public class OrganizationEditController {


    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField nameField;

    private Organization editedOrganization;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleOkButton() {
        editedOrganization.setName(nameField.getText());
        OrganizationService.add(editedOrganization);
        closeWindow();
    }

    @FXML
    private void handleCancelButton() {
       closeWindow();
    }

    public void setEditedOrganization(Organization editedOrganization) {
        this.editedOrganization = editedOrganization;
        nameField.setText(editedOrganization.getName());
    }

    private void closeWindow(){
        Stage thisWindow = (Stage) okButton.getScene().getWindow();
        thisWindow.close();
    }

}
