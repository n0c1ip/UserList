package controllers;

import crudDB.ExtendedRevisionService;
import crudDB.NetworkEquipmentService;
import crudDB.PhysicalServerService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.ExtendedRevisionEntity;
import objects.NetworkEquipment;
import objects.PhysicalServer;
import util.ActiveUser;
import util.I18n;
import util.Permission;

public class NetworkEquipmentTableController {

    private MainController mainController;
    @FXML
    private Button addButton;
    @FXML
    private Button changeButton;
    @FXML
    private Button removeButton;
    @FXML
    private TableView<NetworkEquipment> tableView;
    @FXML
    private TableColumn<NetworkEquipment, String> neNameColumn;
    @FXML
    private TableColumn<NetworkEquipment, String> neModelColumn;
    @FXML
    private TableColumn<NetworkEquipment, String> neTypeColumn;
    @FXML
    private TableColumn<NetworkEquipment, String> neIpColumn;
    @FXML
    private TableColumn<NetworkEquipment, String> neLocationColumn;
    @FXML
    private TableColumn<NetworkEquipment, String> enDescriptionColumn;

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
                    handleEditPcButton();
                }
            });
        } else {
            addButton.setDisable(true);
            changeButton.setDisable(true);
            removeButton.setDisable(true);
        }

        showAllNetworkEquipment();

        neNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        neModelColumn.setCellValueFactory(cellData -> cellData.getValue().getModelProperty());
        neTypeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        neIpColumn.setCellValueFactory(cellData -> cellData.getValue().getIpAddressProperty());
        neLocationColumn.setCellValueFactory(cellData -> cellData.getValue().getLocationProperty());
        enDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
    }

    private void initiateContextMenu(){
        MenuItem lastEdit = new MenuItem(I18n.TABLE.getString("ContextMenu.LastEdit"));
        contextMenu = new ContextMenu(lastEdit);
        lastEdit.setOnAction(event -> {
            NetworkEquipment selectedEquipment = tableView.getSelectionModel().getSelectedItem();
            if (selectedEquipment != null) {
                ExtendedRevisionEntity revisionEntity =
                        ExtendedRevisionService.getLastRevisionEntity(NetworkEquipment.class, selectedEquipment);
                DialogController.showLastEditDialog(revisionEntity.getUserName(), revisionEntity.getRevisionDate());
            }
        });
    }

    private void showAllNetworkEquipment(){
        tableView.setItems(FXCollections.observableArrayList(NetworkEquipmentService.getAll()));
    }

    @FXML
    private void hadnleNewPcButton(){
        mainController.getDialogController().showPServerEditDialog("Создание сервера", new PhysicalServer());
    }

    @FXML
    private void handleEditPcButton(){
        NetworkEquipment ne = tableView.getSelectionModel().getSelectedItem();
//        mainController.getDialogController().showPServerEditDialog("Редактирование сервера", ne);
    }

    @FXML
    private void handleRemovePcButton(){
        NetworkEquipment ne = tableView.getSelectionModel().getSelectedItem();
        if(ne != null){
            PhysicalServerService.delete(ne.getId());
            showAllNetworkEquipment();
        } else {
            DialogController.showErrorDialog("Не выбран сервер");
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
