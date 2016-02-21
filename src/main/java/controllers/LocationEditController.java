package controllers;

import crudDB.LocationService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Location;

public class LocationEditController {

    Location editedLocation;
    
    @FXML
    TextField locationNameField;
        
    @FXML
    TextField locationCityField;
    
    @FXML
    TextField locationAddressField;
    
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

        LocationService.update(editedLocation);

        closeWindow();
    }

    public void handleCancelButton() {
        closeWindow();
    }

    private void closeWindow(){
        Stage thisWindow = (Stage) locationNameField.getScene().getWindow();
        thisWindow.close();
    }
}
