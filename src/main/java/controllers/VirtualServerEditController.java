package controllers;

import crudDB.PhysicalServerService;
import crudDB.VirtualServerService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.PhysicalServer;
import objects.VirtualServer;

public class VirtualServerEditController {

    @FXML
    private TextField serverNameField;
    @FXML
    private TextField serverRamField;
    @FXML
    private TextField serverIpAddressField;
    @FXML
    private TextField serverHddField;
    @FXML
    private TextField backupServerField;
    @FXML
    private TextField backupPathField;
    @FXML
    private TextField scsiHostField;
    @FXML
    private TextField serverOsField;
    @FXML
    private TextField serverBackupTypeField;
    @FXML
    private TextField backupTimeField;
    @FXML
    private TextField scsiTargetField;
    @FXML
    private TextField scsiHddField;
    @FXML
    private TextField serverDescriptionField;
    @FXML
    private ComboBox<PhysicalServer> hostComboBox;

    private VirtualServer editedServer;

    @FXML
    private void initialize() {
        hostComboBox.setItems(FXCollections.observableArrayList(PhysicalServerService.getAll()));
    }

    public void handleOkButton(){
        editedServer.setName(serverNameField.getText());
        if(hostComboBox.getValue() != null){
            editedServer.setpServer(hostComboBox.getValue());
        }
        editedServer.setIpAddress(serverIpAddressField.getText());
        editedServer.setRam(serverRamField.getText());
        editedServer.setHdd(serverHddField.getText());
        editedServer.setOs(serverOsField.getText());

        editedServer.setBackupType(serverBackupTypeField.getText());
        editedServer.setBackupTime(backupTimeField.getText());
        editedServer.setBackupServer(backupServerField.getText());
        editedServer.setBackupPath(backupPathField.getText());

        editedServer.setScsiHdd(scsiHddField.getText());
        editedServer.setScsiHost(scsiHostField.getText());
        editedServer.setScsiTarget(scsiTargetField.getText());

        editedServer.setDescription(serverDescriptionField.getText());
        VirtualServerService.add(editedServer);
        closeWindow();
    }

    @FXML
    private void handleCancelButton() {
        closeWindow();
    }

    private void closeWindow(){
        Stage thisWindow = (Stage) serverNameField.getScene().getWindow();
        thisWindow.close();
    }

    public void setEditedServer(VirtualServer vServer) {
        this.editedServer = vServer;
        serverNameField.setText(vServer.getName());
        if(editedServer.getpServer() != null){
            hostComboBox.setValue(editedServer.getpServer());
        }
        serverIpAddressField.setText(vServer.getIpAddress());
        serverRamField.setText(vServer.getRam());
        serverHddField.setText(editedServer.getHdd());
        serverOsField.setText(vServer.getOs());
        serverBackupTypeField.setText(editedServer.getBackupType());
        backupTimeField.setText(editedServer.getBackupTime());
        backupServerField.setText(editedServer.getBackupServer());
        backupPathField.setText(editedServer.getBackupPath());
        scsiHddField.setText(editedServer.getScsiHdd());
        scsiHostField.setText(editedServer.getScsiHost());
        scsiTargetField.setText(editedServer.getScsiTarget());
        serverDescriptionField.setText(vServer.getDescription());
    }

}
