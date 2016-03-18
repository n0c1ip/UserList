package controllers;

import crudDB.ExtendedRevisionService;
import crudDB.VlanService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.ExtendedRevisionEntity;
import objects.Vlan;
import util.ActiveUser;
import util.I18n;
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

    private void initiateContextMenu(){
        MenuItem lastEdit = new MenuItem(I18n.TABLE.getString("ContextMenu.LastEdit"));
        contextMenu = new ContextMenu(lastEdit);
        lastEdit.setOnAction(event -> {
            Vlan selectedVlan = tableView.getSelectionModel().getSelectedItem();
            if (selectedVlan != null) {
                ExtendedRevisionEntity revisionEntity = ExtendedRevisionService.getLastRevisionEntity(Vlan.class, selectedVlan);
                DialogController.showLastEditDialog(revisionEntity.getUserName(), revisionEntity.getRevisionDate());
            }
        });
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
