package controllers;

import crudDB.OrganizationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import objects.Organization;

public class OrganizationTableController {

    private MainController mainController;
    @FXML
    private TableView<Organization> organizationTable;
    @FXML
    private TableColumn<Organization, String> organizationNameColumn;

    @FXML
    private void initialize(){
        showAllOrganizations();

        organizationNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());

        organizationTable.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                handleEditOrganizationButton();
            }
        });
    }

    private void showAllOrganizations(){
        ObservableList<Organization> organizationList = FXCollections.observableArrayList();
        organizationList.setAll(OrganizationService.getAll());
        organizationTable.setItems(organizationList);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleEditOrganizationButton() {
        Organization selectedOrganization = organizationTable.getSelectionModel().getSelectedItem();
        if (selectedOrganization != null) {
            mainController.getDialogController().showOrganizationEditDialog("Редактирование организации", selectedOrganization);
            showAllOrganizations();
        }
    }

    @FXML
    private void handleNewOrganizationButton() {
        Organization organization = new Organization();
        mainController.getDialogController().showOrganizationEditDialog("Добавить организацию", organization);
        showAllOrganizations();
    }

    @FXML
    private void handleDeleteOrganization() {
        int selectedIndex = organizationTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Organization organizationToDelete = organizationTable.getSelectionModel().getSelectedItem();
            OrganizationService.delete(organizationToDelete.getId());
            showAllOrganizations();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainController.getPrimaryStage());
            alert.setTitle("Не выбрана организация");
            alert.setHeaderText("Не выбрана организация");
            alert.setContentText("Сначала выберите организацию");
            alert.showAndWait();
        }

    }

}