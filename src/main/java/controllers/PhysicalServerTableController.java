package controllers;

import crudDB.ExtendedRevisionService;
import crudDB.PhysicalServerService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.ExtendedRevisionEntity;
import objects.PhysicalServer;
import util.ActiveUser;
import util.I18n;
import util.Permission;

public class PhysicalServerTableController {

    private MainController mainController;
    @FXML
    private Button addButton;
    @FXML
    private Button changeButton;
    @FXML
    private Button removeButton;
    @FXML
    private TableView<PhysicalServer> tableView;
    @FXML
    private TableColumn<PhysicalServer, String> serverNameColumn;
    @FXML
    private TableColumn<PhysicalServer, String> serverModelColumn;
    @FXML
    private TableColumn<PhysicalServer, String> serverTypeColumn;
    @FXML
    private TableColumn<PhysicalServer, String> serverIpAddressColumn;
    @FXML
    private TableColumn<PhysicalServer, String> serverRamColumn;
    @FXML
    private TableColumn<PhysicalServer, String> serverOsColumn;
    @FXML
    private TableColumn<PhysicalServer, String> serverDescriptionColumn;


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
        serverModelColumn.setCellValueFactory(cellData -> cellData.getValue().getModelProperty());
        serverTypeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        serverIpAddressColumn.setCellValueFactory(cellData -> cellData.getValue().getIpAddressProperty());
        serverRamColumn.setCellValueFactory(cellData -> cellData.getValue().getRamProperty());
        serverOsColumn.setCellValueFactory(cellData -> cellData.getValue().getOsProperty());
        serverDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
    }

    private void initiateContextMenu(){
        MenuItem lastEdit = new MenuItem(I18n.TABLE.getString("ContextMenu.LastEdit"));
        contextMenu = new ContextMenu(lastEdit);
        lastEdit.setOnAction(event -> {
            PhysicalServer selectedServer = tableView.getSelectionModel().getSelectedItem();
            if (selectedServer != null) {
                ExtendedRevisionEntity revisionEntity =
                        ExtendedRevisionService.getLastRevisionEntity(PhysicalServer.class, selectedServer);
                DialogController.showLastEditDialog(revisionEntity.getUserName(), revisionEntity.getRevisionDate());
            }
        });
    }

    private void showAllServers(){
        tableView.setItems(FXCollections.observableArrayList(PhysicalServerService.getAll()));
    }

    @FXML
    private void hadnleNewPcButton(){
        mainController.getDialogController().showPServerEditDialog("Создание сервера", new PhysicalServer());
    }

    @FXML
    private void handleEditPcButton(){
        PhysicalServer selectedServer = tableView.getSelectionModel().getSelectedItem();
        mainController.getDialogController().showPServerEditDialog("Редактирование сервера", selectedServer);
    }

    @FXML
    private void handleRemovePcButton(){
        PhysicalServer selectedServer = tableView.getSelectionModel().getSelectedItem();
        if(selectedServer != null){
            PhysicalServerService.delete(selectedServer.getId());
            showAllServers();
        } else {
            DialogController.showErrorDialog("Не выбран сервер");
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
