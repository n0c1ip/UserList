package controllers;

import crudDB.VlanService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import objects.Vlan;
import util.ActiveUser;
import util.Permission;

public class VlanTableController {

    private MainController mainController;

    @FXML
    private Button addButton;
    @FXML
    private Button changeButton;
    @FXML
    private Button removeButton;
    @FXML
    private TableView<Vlan> tableView;
    @FXML
    private TableColumn<Vlan, String> vlanNumberColumn;
    @FXML
    private TableColumn<Vlan, String> networkColumn;
    @FXML
    private TableColumn<Vlan, String> descriptionColumn;


    @FXML
    private void initialize(){
        if (ActiveUser.hasPermission(Permission.WRITE)) {
            tableView.setOnMousePressed(event -> {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    handleEditVlanButton();
                }
            });
        } else {
            addButton.setDisable(true);
            changeButton.setDisable(true);
            removeButton.setDisable(true);
        }
        showAllVlans();

        vlanNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getNumberProperty());
        networkColumn.setCellValueFactory(cellData -> cellData.getValue().getNetworkProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
    }

    public void handleNewVlanButton() {
        Vlan newVlan = new Vlan();
        mainController.getDialogController().showVlanEditDialog("Создание VLAN", newVlan);
        showAllVlans();
    }

    public void handleDeleteVlanButton() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Vlan vlanToDelete = tableView.getSelectionModel().getSelectedItem();
            VlanService.delete(vlanToDelete.getId());
            showAllVlans();
        } else {
            DialogController.showErrorDialog("Сначала выберите VLAN");
        }
    }

    public void handleEditVlanButton() {
        Vlan selectedVlan = tableView.getSelectionModel().getSelectedItem();
        if (selectedVlan != null) {
            mainController.getDialogController().showVlanEditDialog("Редактирование VLAN", selectedVlan);
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void showAllVlans(){
        ObservableList<Vlan> vlanList = FXCollections.observableArrayList(VlanService.getAll());
        tableView.setItems(vlanList);
    }
}
