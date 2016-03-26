package controllers;

import crudDB.BeanValidation;
import crudDB.NetworkEquipmentService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.NetworkEquipment;

public class NetworkEquipmentEditController {

    @FXML
    private TextField neNameField;
    @FXML
    private TextField neIpAddressField;
    @FXML
    private TextField neModelField;
    @FXML
    private TextField neTypeField;
    @FXML
    private TextField neLocationField;
    @FXML
    private TextField neDescriptionField;
    @FXML
    private Button okButton;

    private NetworkEquipment ne;

    @FXML
    private void initialize() {
    }

    public void handleOkButton(){
        if (BeanValidation.isCorrectData(ne)) {
            ne.setName(neNameField.getText());
            ne.setIpAddress(neIpAddressField.getText());
            ne.setModel(neModelField.getText());
            ne.setType(neTypeField.getText());
            ne.setLocation(neLocationField.getText());
            ne.setDescription(neDescriptionField.getText());
            NetworkEquipmentService.add(ne);
            closeWindow();
        } else {
            DialogController.showErrorDialog(BeanValidation.getViolationsText(ne));
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

    public void setEditedNetworkEquipment(NetworkEquipment ne) {
        this.ne = ne;
        neNameField.setText(ne.getName());
        neIpAddressField.setText(ne.getIpAddress());
        neModelField.setText(ne.getModel());
        neTypeField.setText(ne.getType());
        neLocationField.setText(ne.getLocation());
        neDescriptionField.setText(ne.getDescription());
    }
}
