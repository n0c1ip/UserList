package controllers;

import crudDB.PcService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Pc;
import util.I18n;

public class PcEditController {

    @FXML
    private TextField pcNameField;
    @FXML
    private TextField ipAddressField;
    @FXML
    private TextField vlanField;
    @FXML
    private TextField userField;
    @FXML
    private ComboBox<String> ipAddressType;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    private DialogController dialogController;
    private Pc editedPc;


    @FXML
    private void initialize() {
        ObservableList<String> addressTypes =
                FXCollections.observableArrayList(
                        "DHCP",
                        "Static"
                );

        ipAddressType.setItems(addressTypes);
    }

    public void handleSelectButton() {
        if(editedPc != null){
            dialogController.showExistingUserInDepartmentChoiceDialog(I18n.DIALOG.getString("Title.AddUsers"), editedPc);
        } else {
            DialogController.showErrorDialog("Необходимо выбрать классификатор");
        }
    }

    public void handleOkButton(){
        editedPc.setName(pcNameField.getText());
        editedPc.setIpAddress(ipAddressField.getText());
        editedPc.setVlan(vlanField.getText());
        if(ipAddressType.getSelectionModel().getSelectedItem().equals("DHCP")){
            editedPc.setDhcp(true);
        } else {
            editedPc.setDhcp(false);
        }
        PcService.add(editedPc);
        closeWindow();
    }

    @FXML
    private void handleCancelButton() {
        closeWindow();
    }

    private void closeWindow(){
        Stage thisWindow = (Stage) okButton.getScene().getWindow();
        thisWindow.close();
    }
    public void setDialogController(DialogController dialogController) {
        this.dialogController = dialogController;
    }
    public void setEditedPc(Pc editedPc) {
        this.editedPc = editedPc;
        pcNameField.setText(editedPc.getName());
        ipAddressField.setText(editedPc.getIpAddress());
        vlanField.setText(editedPc.getVlan());
        if(editedPc.isDhcp()){
            ipAddressType.getSelectionModel().select("DHCP");
        } else {
            ipAddressType.getSelectionModel().select("Static");
        }
        if(editedPc.getUser() != null){
            userField.setText(editedPc.getUser().getFullName());
        }
    }


}
