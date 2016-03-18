package controllers;

import crudDB.ExtendedRevisionService;
import crudDB.PcService;
import crudDB.UserService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.ExtendedRevisionEntity;
import objects.Pc;
import objects.User;
import util.ActiveUser;
import util.I18n;
import util.Permission;

public class PcTableController {

    private MainController mainController;
    @FXML
    private Button addButton;
    @FXML
    private Button changeButton;
    @FXML
    private Button removeButton;
    @FXML
    private TableView<Pc> tableView;
    @FXML
    private TableColumn<Pc, String> pcNameColumn;
    @FXML
    private TableColumn<Pc, String> pcUserName;
    @FXML
    private TableColumn<Pc, String> pcIpAddress;
    @FXML
    private TableColumn<Pc, String> pcVlan;
    @FXML
    private TableColumn<Pc, String> pcIpAddressType;

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

        showAllPc();

        pcNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        pcIpAddress.setCellValueFactory(cellData -> cellData.getValue().getIpAddressProperty());
        pcVlan.setCellValueFactory(cellData -> cellData.getValue().getVlanProperty());
        pcUserName.setCellValueFactory(cellData -> {
            if(cellData.getValue().getUser() != null){
                return cellData.getValue().getUser().getFullNameProperty();
            }
            return new SimpleStringProperty("");
        });
        pcIpAddressType.setCellValueFactory(cellData ->{
            if(cellData.getValue().isDhcp()){
                return new SimpleStringProperty("DHCP");
            }else {
                return new SimpleStringProperty("Static");
            }

        });
    }


    private void initiateContextMenu(){
        MenuItem lastEdit = new MenuItem(I18n.TABLE.getString("ContextMenu.LastEdit"));
        contextMenu = new ContextMenu(lastEdit);
        lastEdit.setOnAction(event -> {
            Pc selectedPc = tableView.getSelectionModel().getSelectedItem();
            if (selectedPc != null) {
                ExtendedRevisionEntity revisionEntity = ExtendedRevisionService.getLastRevisionEntity(Pc.class, selectedPc);
                DialogController.showLastEditDialog(revisionEntity.getUserName(), revisionEntity.getRevisionDate());
            }
        });
    }

    private void showAllPc(){
        ObservableList<Pc> pcList = FXCollections.observableArrayList();
        pcList.setAll(PcService.getAll());
        tableView.setItems(pcList);
    }

    @FXML
    private void hadnleNewPcButton(){
        mainController.getDialogController().showPcEditDialog("Создание компьютера", new Pc());
    }

    @FXML
    private void handleEditPcButton(){
        Pc selectedPc = tableView.getSelectionModel().getSelectedItem();
        mainController.getDialogController().showPcEditDialog("Редактирование компьютера", selectedPc);
    }

    @FXML
    private void handleRemovePcButton(){
        Pc selectedPc = tableView.getSelectionModel().getSelectedItem();
        if(selectedPc != null){
            if(selectedPc.getUser() != null){
                User user = selectedPc.getUser();
                user.setPc(null);
                UserService.update(user);
            }
            PcService.delete(selectedPc.getId());
            showAllPc();
        } else {
            DialogController.showErrorDialog("Не выбран пользователь");
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
