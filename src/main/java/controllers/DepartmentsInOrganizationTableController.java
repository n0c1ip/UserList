package controllers;

import crudDB.DepartmentService;
import crudDB.OrganizationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.Department;
import objects.Organization;
import util.I18n;

public class DepartmentsInOrganizationTableController {

    private MainController mainController;
    @FXML
    private ListView<Organization> organizationListView;
    @FXML
    private TableView<Department> tableView;
    @FXML
    private TableColumn<Department, String> departmentNameColumn;

    @FXML
    private void initialize(){
        ObservableList<Organization> organizationsList = FXCollections.observableArrayList();
        organizationsList.setAll(OrganizationService.getAll());
        organizationListView.setItems(organizationsList.sorted());
        organizationListView.getSelectionModel().selectFirst();
        showDepartmentByOrganizationSelect(organizationListView.getSelectionModel().getSelectedItem());
        organizationListView.getSelectionModel().selectedItemProperty().addListener(
                (observable1, oldValue, newValue ) -> showDepartmentByOrganizationSelect(newValue));

        departmentNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());

        tableView.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                handleEditDepartmentButton();
            }
        });

    }

    private void showDepartmentByOrganizationSelect(Organization organization){
        ObservableList<Department> departmentList = FXCollections.observableArrayList();
        departmentList.setAll(DepartmentService.getByOrganization(organization));
        tableView.setItems(departmentList);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleEditDepartmentButton() {
        Department selectedDepartment = tableView.getSelectionModel().getSelectedItem();
        if (selectedDepartment != null) {
            mainController.getDialogController().showDepartmentEditDialog(I18n.DIALOG.getString("Title.EditDepartment"),
                                        selectedDepartment, organizationListView.getSelectionModel().getSelectedItem());
            showDepartmentByOrganizationSelect(organizationListView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void handleNewDepartmentButton() {
        Department department = new Department();
        mainController.getDialogController().showDepartmentEditDialog(I18n.DIALOG.getString("Title.AddDepartment"),
                                            department, organizationListView.getSelectionModel().getSelectedItem());
        showDepartmentByOrganizationSelect(organizationListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleDeleteDepartment() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Department departmentToDelete = tableView.getSelectionModel().getSelectedItem();
            DepartmentService.delete(departmentToDelete.getId());
            showDepartmentByOrganizationSelect(organizationListView.getSelectionModel().getSelectedItem());
        } else {
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Не выбрано подразделение", "Сначала выберите подразделение");
        }

    }

}
