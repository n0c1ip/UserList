package controllers;

import crudDB.ExtendedRevisionService;
import crudDB.NetworkService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.ExtendedRevisionEntity;
import objects.Network;
import util.ActiveUser;
import util.I18n;
import util.Permission;

public class NetworkTableController {

    private MainController mainController;

    @FXML
    private Button addButton;
    @FXML
    private Button changeButton;
    @FXML
    private Button removeButton;
    @FXML
    private TableView<Network> tableView;
    @FXML
    private TableColumn<Network, String> networkColumn;
    @FXML
    private TableColumn<Network, String> descriptionColumn;

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
                    handleEditNetworkButton();
                }
            });
        } else {
            addButton.setDisable(true);
            changeButton.setDisable(true);
            removeButton.setDisable(true);
        }
        showAllNetworks();

        networkColumn.setCellValueFactory(cellData -> cellData.getValue().getNetworkProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
    }


    private void initiateContextMenu(){
        MenuItem lastEdit = new MenuItem(I18n.TABLE.getString("ContextMenu.LastEdit"));
        contextMenu = new ContextMenu(lastEdit);
        lastEdit.setOnAction(event -> {
            Network selectedNetwork = tableView.getSelectionModel().getSelectedItem();
            if (selectedNetwork != null) {
                ExtendedRevisionEntity revisionEntity = ExtendedRevisionService.getLastRevisionEntity(Network.class, selectedNetwork);
                DialogController.showLastEditDialog(revisionEntity.getUserName(), revisionEntity.getRevisionDate());
            }
        });
    }

    public void handleNewNetworkButton() {
        Network newNetwork = new Network();
        mainController.getDialogController().showNetworkEditDialog("Создание подсети", newNetwork);
        showAllNetworks();
    }

    public void handleDeleteNetworkButton() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Network networkToDelete = tableView.getSelectionModel().getSelectedItem();
            NetworkService.delete(networkToDelete.getId());
            showAllNetworks();
        } else {
            DialogController.showErrorDialog("Сначала выберите подсеть");
        }
    }

    public void handleEditNetworkButton() {
        Network selectedNetwork = tableView.getSelectionModel().getSelectedItem();
        if (selectedNetwork != null) {
            mainController.getDialogController().showNetworkEditDialog("Редактирование подсети", selectedNetwork);
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void showAllNetworks(){
        ObservableList<Network> networkList = FXCollections.observableArrayList(NetworkService.getAll());
        tableView.setItems(networkList);
    }
}
