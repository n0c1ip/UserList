package controllers;

import crudDB.BeanValidation;
import crudDB.NetworkService;
import crudDB.VlanService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Network;
import objects.Vlan;

public class VlanEditController {

    private Vlan editedVlan;

    @FXML
    private TextField vlanNumberField;

    @FXML
    private ComboBox<Network> networkComboBox;
        
    @FXML
    private TextArea descriptionArea;

    @FXML
    private void initialize(){
        networkComboBox.setItems(FXCollections.observableArrayList(NetworkService.getAll()));
    }

    public void setEditedVlan(Vlan editedVlan) {
        this.editedVlan = editedVlan;

        vlanNumberField.setText(editedVlan.getNumber());
        networkComboBox.getSelectionModel().select(editedVlan.getNetwork());
        descriptionArea.setText(editedVlan.getDescription());
    }

    public void handleOkButton() {
        editedVlan.setNumber(vlanNumberField.getText());
        editedVlan.setNetwork(networkComboBox.getValue());
        editedVlan.setDescription(descriptionArea.getText());
        if (BeanValidation.isCorrectData(editedVlan)) {
            VlanService.update(editedVlan);
            closeWindow();
        } else {
            DialogController.showErrorDialog(BeanValidation.getViolationsText(editedVlan));
        }
    }

    public void handleCancelButton() {
        closeWindow();
    }

    private void closeWindow(){
        Stage thisWindow = (Stage) vlanNumberField.getScene().getWindow();
        thisWindow.close();
    }

}
