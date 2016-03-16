package controllers;

import crudDB.BeanValidation;
import crudDB.NetworkService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Network;

public class NetworkEditController {

    private Network editedNetwork;
    
    @FXML
    private TextField networkField;
        
    @FXML
    private TextArea descriptionArea;

    public void setEditedNetwork(Network editedNetwork) {
        this.editedNetwork = editedNetwork;

        networkField.setText(editedNetwork.getNetwork());
        descriptionArea.setText(editedNetwork.getDescription());
    }

    public void handleOkButton() {
        editedNetwork.setNetwork(networkField.getText());
        editedNetwork.setDescription(descriptionArea.getText());
        if (BeanValidation.isCorrectData(editedNetwork)) {
            NetworkService.update(editedNetwork);
            closeWindow();
        } else {
            DialogController.showErrorDialog(BeanValidation.getViolationsText(editedNetwork));
        }
    }

    public void handleCancelButton() {
        closeWindow();
    }

    private void closeWindow(){
        Stage thisWindow = (Stage) networkField.getScene().getWindow();
        thisWindow.close();
    }

}
