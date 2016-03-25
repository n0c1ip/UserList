package controllers;

import crudDB.VirtualServerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objects.PhysicalServer;
import objects.VirtualServer;

public class VirtualServerInHostTableController {

    private MainController mainController;
    private PhysicalServer physicalServer;

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
    private Stage stage;

    @FXML
    private void initialize(){

        showAllServers(); //physicalServer is null, fuck !!! //TODO

        serverNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        vmHostColumn.setCellValueFactory(cellData -> cellData.getValue().getPhysicalServerProperty());
        serverDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        serverOsColumn.setCellValueFactory(cellData -> cellData.getValue().getOsProperty());
        serverHddColumn.setCellValueFactory(cellData -> cellData.getValue().getHddProperty());
        serverIpAddressColumn.setCellValueFactory(cellData -> cellData.getValue().getIpAddressProperty());
        serverRamColumn.setCellValueFactory(cellData -> cellData.getValue().getRamProperty());
    }

    private void showAllServers(){
        tableView.setItems(FXCollections.observableArrayList(VirtualServerService.getByPServer(physicalServer)));
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setPhysicalServer(PhysicalServer physicalServer) {
        this.physicalServer = physicalServer;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
