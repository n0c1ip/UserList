package controllers;

import crudDB.BeanValidation;
import crudDB.PhysicalServerService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.PhysicalServer;

public class PhysicalServerEditController {

    @FXML
    private TextField serverNameField;
    @FXML
    private TextField serverModelField;
    @FXML
    private TextField serverIpAddressField;
    @FXML
    private ComboBox<PhysicalServer.TYPE> typeComboBox;
    @FXML
    private TextField serverRamField;
    @FXML
    private TextField serverOsField;
    @FXML
    private TextField serverDescriptionField;
    @FXML
    private Button okButton;
    @FXML
    private CheckBox vmHostCheckBox;

    private PhysicalServer editedServer;

    @FXML
    private void initialize() {
        typeComboBox.setItems(FXCollections.observableArrayList(PhysicalServer.TYPE.values()));
    }

    public void handleOkButton(){
        if (BeanValidation.isCorrectData(editedServer)) {
            editedServer.setName(serverNameField.getText());
            editedServer.setModel(serverModelField.getText());
            editedServer.setIpAddress(serverIpAddressField.getText());
            editedServer.setType(typeComboBox.getValue());
            editedServer.setRam(serverRamField.getText());
            editedServer.setOs(serverOsField.getText());
            editedServer.setDescription(serverDescriptionField.getText());
            editedServer.setVirtualHost(vmHostCheckBox.isSelected());
            PhysicalServerService.add(editedServer);
            closeWindow();
        }
        else{
            DialogController.showErrorDialog(BeanValidation.getViolationsText(editedServer));
        }
    }

    @FXML
    private void handleCancelButton() {
        closeWindow();
    }

    private void closeWindow(){
        Stage thisWindow = (Stage) okButton.getScene().getWindow();
        thisWindow.close();
    }

    public void setEditedServer(PhysicalServer pServer) {
        this.editedServer = pServer;
        serverNameField.setText(pServer.getName());
        serverModelField.setText(pServer.getModel());
        serverIpAddressField.setText(pServer.getIpAddress());
        typeComboBox.getSelectionModel().select(pServer.getType());
        serverRamField.setText(pServer.getRam());
        serverOsField.setText(pServer.getOs());
        serverDescriptionField.setText(pServer.getDescription());
        vmHostCheckBox.setSelected(pServer.isVirtualHost());
    }
}
