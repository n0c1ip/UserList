package controllers;

import crudDB.ExtendedRevisionService;
import crudDB.UserSignUnlimitedService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.ExtendedRevisionEntity;
import objects.User;
import objects.UserSignUnlimited;
import util.ActiveUser;
import util.I18n;
import util.Permission;

public class UserSignUnlimitedTableController {

    @FXML
    private Button addButton;
    @FXML
    private Button changeButton;
    @FXML
    private Button removeButton;
    private MainController mainController;
    @FXML
    private TableView<UserSignUnlimited> tableView;
    @FXML
    private TableColumn<UserSignUnlimited, String> signUnlimitedNameColumn;
    @FXML
    private TableColumn<UserSignUnlimited, String> userSignUnlimitedValueColumn;

    private User user;
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
                    handleEditUserSignUnlimitedButton();
                }
            });
        } else {
            addButton.setDisable(true);
            changeButton.setDisable(true);
            removeButton.setDisable(true);
        }
        showUserUnlimitedSigns();

        signUnlimitedNameColumn.setCellValueFactory(cellData -> cellData.getValue().getSignUnlimited().getNameProperty());
        userSignUnlimitedValueColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
    }


    private void initiateContextMenu(){
        MenuItem lastEdit = new MenuItem(I18n.TABLE.getString("ContextMenu.LastEdit"));
        contextMenu = new ContextMenu(lastEdit);
        lastEdit.setOnAction(event -> {
            UserSignUnlimited selectedUserSignUnlimited = tableView.getSelectionModel().getSelectedItem();
            if (selectedUserSignUnlimited != null) {
                ExtendedRevisionEntity revisionEntity = ExtendedRevisionService.getLastRevisionEntity(UserSignUnlimited.class, selectedUserSignUnlimited);
                DialogController.showLastEditDialog(revisionEntity.getUserName(), revisionEntity.getRevisionDate());
            }
        });
    }

    public void showUserUnlimitedSigns(){
        ObservableList<UserSignUnlimited> UserSignUnlimitedList = FXCollections.observableArrayList();
        UserSignUnlimitedList.setAll(UserSignUnlimitedService.getByUser(user));
        tableView.setItems(UserSignUnlimitedList);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleEditUserSignUnlimitedButton() {
        UserSignUnlimited selectedUserSignUnlimited = tableView.getSelectionModel().getSelectedItem();
        if (selectedUserSignUnlimited != null) {
            mainController.getDialogController().showUserSignUnlimitedEditDialog(I18n.DIALOG.getString("UserSign.Edit"), selectedUserSignUnlimited);
            showUserUnlimitedSigns();
        } else {
            DialogController.showErrorDialog("Сначала выберите признак");
        }
    }

    @FXML
    private void handleNewUserSignUnlimitedButton() {
        UserSignUnlimited userSignUnlimited = new UserSignUnlimited();
        userSignUnlimited.setUser(user);
        mainController.getDialogController().showUserSignUnlimitedEditDialog(I18n.DIALOG.getString("UserSign.Add"), userSignUnlimited);
        showUserUnlimitedSigns();
    }

    @FXML
    private void handleDeleteUserSignUnlimited() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            UserSignUnlimited userSignUnlimitedToDelete = tableView.getSelectionModel().getSelectedItem();
            UserSignUnlimitedService.delete(userSignUnlimitedToDelete.getId());
            showUserUnlimitedSigns();
        } else {
            DialogController.showErrorDialog("Сначала выберите признак");
        }

    }

    public void setUser(User user) {
        this.user = user;
    }

}
