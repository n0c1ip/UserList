package controllers;

import crudDB.OrganizationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import objects.Organization;
import util.ActiveUser;
import util.I18n;
import util.Permission;

public class OrganizationTableController {

    private MainController mainController;

    @FXML
    private Button addButton;
    @FXML
    private Button changeButton;
    @FXML
    private Button removeButton;
    @FXML
    private TableView<Organization> tableView;
    @FXML
    private TableColumn<Organization, String> organizationNameColumn;

    @FXML
    private void initialize(){
        if (ActiveUser.hasPermission(Permission.WRITE)) {
            tableView.setOnMousePressed(event -> {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    handleEditOrganizationButton();
                }
            });
        } else {
            addButton.setDisable(true);
            changeButton.setDisable(true);
            removeButton.setDisable(true);
        }

        showAllOrganizations();

        organizationNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
    }

    private void showAllOrganizations(){
        ObservableList<Organization> organizationList = FXCollections.observableArrayList();
        organizationList.setAll(OrganizationService.getAll());
        tableView.setItems(organizationList);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleEditOrganizationButton() {
        Organization selectedOrganization = tableView.getSelectionModel().getSelectedItem();
        if (selectedOrganization != null) {
            mainController.getDialogController().showOrganizationEditDialog(I18n.DIALOG.getString("Title.EditOrganization"), selectedOrganization);
            showAllOrganizations();
        }
    }

    @FXML
    private void handleNewOrganizationButton() {
        Organization organization = new Organization();
        mainController.getDialogController().showOrganizationEditDialog(I18n.DIALOG.getString("Title.AddOrganization"), organization);
        showAllOrganizations();
    }

    @FXML
    private void handleDeleteOrganization() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Organization organizationToDelete = tableView.getSelectionModel().getSelectedItem();
            OrganizationService.delete(organizationToDelete.getId());
            showAllOrganizations();
        } else {
            DialogController.showErrorDialog("Сначала выберите организацию");
        }

    }

}
