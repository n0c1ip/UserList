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

import java.io.IOException;

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
    @FXML
    private ListView<PhysicalServer> hostsListView;


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

        hostsListView.setItems(FXCollections.observableArrayList(PhysicalServerService.getAll()));
        hostsListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setVmByHost(newValue));

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
        MenuItem rdpSession = new MenuItem("RDP");
        contextMenu = new ContextMenu(rdpSession,lastEdit);
        lastEdit.setOnAction(event -> {
            VirtualServer selectedServer = tableView.getSelectionModel().getSelectedItem();
            if (selectedServer != null) {
                ExtendedRevisionEntity revisionEntity =
                        ExtendedRevisionService.getLastRevisionEntity(VirtualServer.class, selectedServer);
                DialogController.showLastEditDialog(revisionEntity.getUserName(), revisionEntity.getRevisionDate());
            }
        });
        rdpSession.setOnAction(event -> {
            VirtualServer selectedServer = tableView.getSelectionModel().getSelectedItem();
            try {
                Runtime.getRuntime().exec("mstsc /v:" + selectedServer.getName());
            } catch (IOException e) {
                DialogController.getAlertDialog(Alert.AlertType.ERROR, "Error","Не удалось открыть сессию");
            }
        });
    }

    @FXML
    private void hadnleNewPcButton(){
        if(hostsListView.getSelectionModel().getSelectedItem() != null){
            PhysicalServer thisServer = hostsListView.getSelectionModel().getSelectedItem();
            VirtualServer vs = new VirtualServer();
            vs.setpServer(thisServer);
            mainController.getDialogController().showVServerEditDialog("Создание сервера", vs);
            setVmByHost(thisServer);
        }
    }

    @FXML
    private void handleEditPcButton(){
        VirtualServer selectedServer = tableView.getSelectionModel().getSelectedItem();
        mainController.getDialogController().showVServerEditDialog("Редактирование сервера", selectedServer);
        setVmByHost(hostsListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleRemovePcButton(){
        VirtualServer selectedServer = tableView.getSelectionModel().getSelectedItem();
        if(selectedServer != null){
            VirtualServerService.delete(selectedServer.getId());
            setVmByHost(hostsListView.getSelectionModel().getSelectedItem());
        } else {
            DialogController.showErrorDialog("Не выбран сервер");
        }
    }

    @FXML
    private void setVmByHost(PhysicalServer physicalServer){
        tableView.setItems(FXCollections.observableArrayList(VirtualServerService.getByPServer(physicalServer)));

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
