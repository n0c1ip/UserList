package controllers;

import crudDB.LocationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import objects.Location;
import util.I18n;

public class LocationTableController {

    private MainController mainController;


    @FXML
    private TableView<Location> tableView;

    @FXML
    private TableColumn<Location, String> locationNameColumn;
    @FXML
    private TableColumn<Location, String> locationCityColumn;
    @FXML
    private TableColumn<Location, String> locationAddressColumn;

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
        mainController.getDialogController().showLocationEditDialog(I18n.DIALOG.getString("Title.AddLocation"), newLocation);
        showAllLocations();
    }

    public void handleDeleteLocationButton() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Location locationToDelete = tableView.getSelectionModel().getSelectedItem();
            LocationService.delete(locationToDelete.getId());
            showAllLocations();
        } else {
            DialogController.showErrorDialog("Сначала выберите организацию");
        }
    }

    public void handleEditLocationButton() {
        Location selectedLocation = tableView.getSelectionModel().getSelectedItem();
        if (selectedLocation != null) {
            mainController.getDialogController().showLocationEditDialog(I18n.DIALOG.getString("Title.EditLocation"), selectedLocation);
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
