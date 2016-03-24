package controllers;

import crudDB.ExtendedRevisionService;
import crudDB.PhysicalServerService;
import crudDB.VirtualServerService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.ExtendedRevisionEntity;
import objects.PhysicalServer;
import objects.VirtualServer;
import util.ActiveUser;
import util.I18n;
import util.Permission;

public class VirtualServerTableController {

    private MainController mainController;
    @FXML
    private Button addButton;
    @FXML
    private Button changeButton;
    @FXML
    private Button removeButton;
    @FXML
    private TableView<VirtualServer> tableView;
    @FXML
    private TableColumn<VirtualServer, String> serverNameColumn;
    @FXML
    private TableColumn<VirtualServer, String> vmHostColumn;
    @FXML
    private TableColumn<VirtualServer, String> serverDescriptionColumn;
    @FXML
    private TableColumn<VirtualServer, String> serverOsColumn;
    @FXML
    private TableColumn<VirtualServer, String> serverHddColumn;
    @FXML
    private TableColumn<VirtualServer, String> serverIpAddressColumn;
    @FXML
    private TableColumn<VirtualServer, String> serverRamColumn;


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

        showAllServers();

        serverNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        vmHostColumn.setCellValueFactory(cellData -> cellData.getValue().getPhysicalServerProperty());
        serverDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        serverOsColumn.setCellValueFactory(cellData -> cellData.getValue().getOsProperty());
        serverHddColumn.setCellValueFactory(cellData -> cellData.getValue().getHddProperty());
        serverIpAddressColumn.setCellValueFactory(cellData -> cellData.getValue().getIpAddressProperty());
        serverRamColumn.setCellValueFactory(cellData -> cellData.getValue().getRamProperty());
    }

    private void initiateContextMenu(){
        MenuItem lastEdit = new MenuItem(I18n.TABLE.getString("ContextMenu.LastEdit"));
        contextMenu = new ContextMenu(lastEdit);
        lastEdit.setOnAction(event -> {
            VirtualServer selectedServer = tableView.getSelectionModel().getSelectedItem();
            if (selectedServer != null) {
                ExtendedRevisionEntity revisionEntity =
                        ExtendedRevisionService.getLastRevisionEntity(VirtualServer.class, selectedServer);
                DialogController.showLastEditDialog(revisionEntity.getUserName(), revisionEntity.getRevisionDate());
            }
        });
    }

    private void showAllServers(){
        tableView.setItems(FXCollections.observableArrayList(VirtualServerService.getAll()));
    }

    @FXML
    private void hadnleNewPcButton(){
        mainController.getDialogController().showPServerEditDialog("Создание сервера", new PhysicalServer());
    }

    @FXML
    private void handleEditPcButton(){
        VirtualServer selectedServer = tableView.getSelectionModel().getSelectedItem();
//        mainController.getDialogController().showPServerEditDialog("Редактирование сервера", selectedServer);
    }

    @FXML
    private void handleRemovePcButton(){
        VirtualServer selectedServer = tableView.getSelectionModel().getSelectedItem();
        if(selectedServer != null){
            VirtualServerService.delete(selectedServer.getId());
            showAllServers();
        } else {
            DialogController.showErrorDialog("Не выбран сервер");
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
