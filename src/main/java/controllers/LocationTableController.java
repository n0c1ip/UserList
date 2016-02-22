package controllers;


import crudDB.LocationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import objects.Location;

public class LocationTableController {

    private MainController mainController;


    @FXML
    TableView<Location> tableView;

    @FXML
    TableColumn<Location, String> locationNameColumn;
    @FXML
    TableColumn<Location, String> locationCityColumn;
    @FXML
    TableColumn<Location, String> locationAddressColumn;

    @FXML
    private void initialize(){

        showAllLocations();

        locationNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        locationCityColumn.setCellValueFactory(cellData -> cellData.getValue().getCityProperty());
        locationAddressColumn.setCellValueFactory(cellData -> cellData.getValue().getAdderssProperty());

        tableView.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                handleEditLocationButton();
            }
        });
    }

    public void handleNewLocationButton() {
        Location newLocation = new Location();
        mainController.getDialogController().showLocationEditDialog("Создание объекта", newLocation);
        showAllLocations();
    }

    public void handleDeleteLocationButton() {

    }

    public void handleEditLocationButton() {
        Location selectedLocation = tableView.getSelectionModel().getSelectedItem();
        if (selectedLocation != null) {
            mainController.getDialogController().showLocationEditDialog("Редактирование объекта", selectedLocation);
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void showAllLocations(){
        ObservableList<Location> locationsList = FXCollections.observableArrayList();
        locationsList.setAll(LocationService.getAll());
        tableView.setItems(locationsList);
    }
}
