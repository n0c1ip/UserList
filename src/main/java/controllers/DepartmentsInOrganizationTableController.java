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
    private ListView<Department> departmentListView;
    @FXML
    private ComboBox<Organization> organizationComboBox;
    @FXML
    private TableView<Department> tableView;
    @FXML
    private TableColumn<Department, String> name;

    @FXML
    private void initialize(){
        ObservableList<Organization> organizationsList = FXCollections.observableArrayList();
        organizationsList.setAll(OrganizationService.getAll());
        organizationComboBox.setItems(organizationsList.sorted());
        organizationComboBox.getSelectionModel().selectFirst();
        showDepartmentByOrganizationSelect(organizationComboBox.getValue());
        organizationComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable1, oldValue, newValue ) -> showDepartmentByOrganizationSelect(newValue));

        name.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());

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
            mainController.getDialogController().showDepartmentEditDialog("Редактирование подразделения", selectedDepartment, organizationComboBox.getValue());
            initialize();
        }
    }

    @FXML
    private void handleNewDepartmentButton() {
        Department department = new Department();
        mainController.getDialogController().showDepartmentEditDialog("Добавить подразделение", department, organizationComboBox.getValue());
        initialize();
    }

    @FXML
    private void handleDeleteDepartment() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Department departmentToDelete = tableView.getSelectionModel().getSelectedItem();
            tableView.getItems().remove(selectedIndex);
            DepartmentService.delete(departmentToDelete.getId());
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
