package controllers;

import crudDB.SignUnlimitedService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import objects.SignUnlimited;
import objects.SignUnlimited;

public class SignUnlimitedTableController {

    private MainController mainController;
    @FXML
    private TableView<SignUnlimited> tableView;
    @FXML
    private TableColumn<SignUnlimited, String> signUnlimitedNameColumn;

    @FXML
    private void initialize(){
        showAllUnlimitedSigns();

        signUnlimitedNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());

        tableView.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                handleEditSignUnlimitedButton();
            }
        });
    }

    private void showAllUnlimitedSigns(){
        ObservableList<SignUnlimited> SignUnlimitedList = FXCollections.observableArrayList();
        SignUnlimitedList.setAll(SignUnlimitedService.getAll());
        tableView.setItems(SignUnlimitedList);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleEditSignUnlimitedButton() {
        SignUnlimited selectedSignUnlimited = tableView.getSelectionModel().getSelectedItem();
        if (selectedSignUnlimited != null) {
            mainController.getDialogController().showSignUnlimitedEditDialog("Редактирование признака", selectedSignUnlimited);
            showAllUnlimitedSigns();
        }
    }

    @FXML
    private void handleNewSignUnlimitedButton() {
        SignUnlimited SignUnlimited = new SignUnlimited();
        mainController.getDialogController().showSignUnlimitedEditDialog("Добавить признак", SignUnlimited);
        showAllUnlimitedSigns();
    }

    @FXML
    private void handleDeleteSignUnlimited() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            SignUnlimited SignUnlimitedToDelete = tableView.getSelectionModel().getSelectedItem();
            SignUnlimitedService.delete(SignUnlimitedToDelete.getId());
            showAllUnlimitedSigns();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainController.getPrimaryStage());
            alert.setTitle("Не выбран признак");
            alert.setHeaderText("Не выбран признак");
            alert.setContentText("Сначала выберите признак");
            alert.showAndWait();
        }

    }

}