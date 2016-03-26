package controllers;

import crudDB.ExtendedRevisionService;
import crudDB.LocationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.ExtendedRevisionEntity;
import objects.Location;
import util.ActiveUser;
import util.I18n;
import util.Permission;

public class LocationTableController {

    private MainController mainController;

    @FXML
    private Button addButton;
    @FXML
    private Button changeButton;
    @FXML
    private Button removeButton;
    @FXML
    private TableView<Location> tableView;
    @FXML
    private TableColumn<Location, String> locationNameColumn;
    @FXML
    private TableColumn<Location, String> locationCityColumn;
    @FXML
    private TableColumn<Location, String> locationAddressColumn;

    private ContextMenu contextMenu;

    @FXML
    private void initialize(){
        initiateContextMenu();
        if (ActiveUser.hasPermission(Permission.WRITE)) {
            tableView.setOnMousePressed(event -> {
                if (event.isPrimaryButtonDown() && contextMenu.isShowing()){
                    contextMenu.hide();
                }
                if (event.isSecondaryButtonDown()) {
                    contextMenu.show(tableView,event.getScreenX(),event.getScreenY());
                }
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    handleEditLocationButton();
                }
            });
        } else {
            addButton.setDisable(true);
            changeButton.setDisable(true);
            removeButton.setDisable(true);
        }
        showAllLocations();

        locationNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        locationCityColumn.setCellValueFactory(cellData -> cellData.getValue().getCityProperty());
        locationAddressColumn.setCellValueFactory(cellData -> cellData.getValue().getAdderssProperty());
    }

    private void initiateContextMenu(){
        MenuItem lastEdit = new MenuItem(I18n.TABLE.getString("ContextMenu.LastEdit"));
        contextMenu = new ContextMenu(lastEdit);
        lastEdit.setOnAction(event -> {
            Location selectedLocation = tableView.getSelectionModel().getSelectedItem();
            if (selectedLocation != null) {
                ExtendedRevisionEntity revisionEntity = ExtendedRevisionService.getLastRevisionEntity(Location.class, selectedLocation);
                DialogController.showLastEditDialog(revisionEntity.getUserName(), revisionEntity.getRevisionDate());
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
        showAllLocations();
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
