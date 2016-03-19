package controllers;

import crudDB.DepartmentService;
import crudDB.ExtendedRevisionService;
import crudDB.OrganizationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.Department;
import objects.ExtendedRevisionEntity;
import objects.Organization;
import util.ActiveUser;
import util.I18n;
import util.Permission;

public class DepartmentsInOrganizationTableController {

    private MainController mainController;
    @FXML
    private Button addButton;
    @FXML
    private Button changeButton;
    @FXML
    private Button removeButton;
    @FXML
    private ListView<Organization> organizationListView;
    @FXML
    private TableView<Department> tableView;
    @FXML
    private TableColumn<Department, String> departmentNameColumn;

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
                    handleEditDepartmentButton();
                }
            });
        } else {
            addButton.setDisable(true);
            changeButton.setDisable(true);
            removeButton.setDisable(true);
        }
        ObservableList<Organization> organizationsList = FXCollections.observableArrayList();
        organizationsList.setAll(OrganizationService.getAll());
        organizationListView.setItems(organizationsList.sorted());
        organizationListView.getSelectionModel().selectFirst();
        showDepartmentByOrganizationSelect(organizationListView.getSelectionModel().getSelectedItem());
        organizationListView.getSelectionModel().selectedItemProperty().addListener(
                (observable1, oldValue, newValue) -> showDepartmentByOrganizationSelect(newValue));

        departmentNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
    }

    private void initiateContextMenu(){
        MenuItem lastEdit = new MenuItem(I18n.TABLE.getString("ContextMenu.LastEdit"));
        contextMenu = new ContextMenu(lastEdit);
        lastEdit.setOnAction(event -> {
            Department selectedDepartment = tableView.getSelectionModel().getSelectedItem();
            if (selectedDepartment != null) {
                ExtendedRevisionEntity revisionEntity = ExtendedRevisionService.getLastRevisionEntity(Department.class, selectedDepartment);
                DialogController.showLastEditDialog(revisionEntity.getUserName(), revisionEntity.getRevisionDate());
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
            DialogController.showErrorDialog("Сначала выберите подразделение");
        }

    }

}
