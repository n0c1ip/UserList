package controllers;

import crudDB.BeanValidation;
import crudDB.LocationService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Location;

public class LocationEditController {

    private Location editedLocation;
    
    @FXML
    private TextField locationNameField;
        
    @FXML
    private TextField locationCityField;
    
    @FXML
    private TextField locationAddressField;
    
    public void setEditedLocation(Location editedLocation) {
        this.editedLocation = editedLocation;

        locationNameField.setText(editedLocation.getName());
        locationCityField.setText(editedLocation.getCity());
        locationAddressField.setText(editedLocation.getAddress());

    }

    public void handleOkButton() {
        editedLocation.setName(locationNameField.getText());
        editedLocation.setCity(locationCityField.getText());
        editedLocation.setAddress(locationAddressField.getText());
        if (BeanValidation.isCorrectData(editedLocation)) {
            LocationService.update(editedLocation);
            closeWindow();
        } else {
            DialogController.showErrorDialog(BeanValidation.getViolationsText(editedLocation));
        }
    }

    public void handleCancelButton() {
        closeWindow();
    }

    private void closeWindow(){
        Stage thisWindow = (Stage) locationNameField.getScene().getWindow();
        thisWindow.close();
    }
}
