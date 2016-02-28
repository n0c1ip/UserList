package controllers;

import crudDB.UserSignUnlimitedService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import objects.User;
import objects.UserSignUnlimited;
import util.I18n;

public class UserSignUnlimitedTableController {

    private MainController mainController;
    @FXML
    private TableView<UserSignUnlimited> tableView;
    @FXML
    private TableColumn<UserSignUnlimited, String> signUnlimitedNameColumn;
    @FXML
    private TableColumn<UserSignUnlimited, String> userSignUnlimitedValueColumn;
    private User user;

    @FXML
    private void initialize(){
        showUserUnlimitedSigns();

        signUnlimitedNameColumn.setCellValueFactory(cellData -> cellData.getValue().getSignUnlimited().getNameProperty());
        userSignUnlimitedValueColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());

        tableView.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                handleEditUserSignUnlimitedButton();
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
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Не выбран признак", "Сначала выберите признак");
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
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Не выбран признак", "Сначала выберите признак");
        }

    }

    public void setUser(User user) {
        this.user = user;
    }

}
