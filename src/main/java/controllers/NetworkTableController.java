package controllers;

import crudDB.NetworkService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import objects.Network;
import util.ActiveUser;
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


    @FXML
    private void initialize(){
        if (ActiveUser.hasPermission(Permission.WRITE)) {
            tableView.setOnMousePressed(event -> {
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
