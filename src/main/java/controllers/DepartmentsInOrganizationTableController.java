package controllers;

import crudDB.DepartmentService;
import crudDB.OrganizationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.Department;
import objects.Organization;

public class DepartmentsInOrganizationTableController {

    private MainController mainController;
    @FXML
    private ListView<Organization> organizationListView;
    @FXML
    private TableView<Department> deparmentTable;
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

        deparmentTable.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                handleEditDepartmentButton();
            }
        });

    }

    private void showDepartmentByOrganizationSelect(Organization organization){
        ObservableList<Department> departmentList = FXCollections.observableArrayList();
        departmentList.setAll(DepartmentService.getByOrganization(organization));
        deparmentTable.setItems(departmentList);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleEditDepartmentButton() {
        Department selectedDepartment = deparmentTable.getSelectionModel().getSelectedItem();
        if (selectedDepartment != null) {
            mainController.getDialogController().showDepartmentEditDialog("Редактирование подразделения", selectedDepartment, organizationListView.getSelectionModel().getSelectedItem());
            showDepartmentByOrganizationSelect(organizationListView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void handleNewDepartmentButton() {
        Department department = new Department();
        mainController.getDialogController().showDepartmentEditDialog("Добавить подразделение", department, organizationListView.getSelectionModel().getSelectedItem());
        showDepartmentByOrganizationSelect(organizationListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleDeleteDepartment() {
        int selectedIndex = deparmentTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Department departmentToDelete = deparmentTable.getSelectionModel().getSelectedItem();
            DepartmentService.delete(departmentToDelete.getId());
            showDepartmentByOrganizationSelect(organizationListView.getSelectionModel().getSelectedItem());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainController.getPrimaryStage());
            alert.setTitle("Не выбрано подразделение");
            alert.setHeaderText("Не выбрано подразделение");
            alert.setContentText("Сначала выберите подразделение");
            alert.showAndWait();
        }

    }

}
