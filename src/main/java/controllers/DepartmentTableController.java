package controllers;

import crud.DepartmentService;
import crud.OrganizationService;
import crud.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.Department;
import objects.Organization;
import objects.User;

public class DepartmentTableController {

    private MainController mainController;
    @FXML
    private ListView<Department> departmentListView;
    @FXML
    private ComboBox<Organization> organizationComboBox;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> middleNameColumn;
    @FXML
    private TableColumn<User, String> departmentColumn;
    @FXML
    private TableColumn<User, String> positionColumn;
    @FXML
    private TableColumn<User, String> loginColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> mailColumn;


    @FXML
    private void initialize(){
        //Organization ComboBox
        ObservableList<Organization> organizationsList = FXCollections.observableArrayList();
        organizationsList.setAll(OrganizationService.getAll());
        organizationComboBox.setItems(organizationsList.sorted());
        organizationComboBox.getSelectionModel().selectFirst();
        showDepartmentByOrganizationSelect(organizationComboBox.getValue());
        organizationComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable1, oldValue, newValue ) -> showDepartmentByOrganizationSelect(newValue));

        //Departments ListView
        departmentListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showUserByDepartments(newValue));

        //User table
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().getDepartmentProperty());
        middleNameColumn.setCellValueFactory(cellData -> cellData.getValue().getMiddleNameProperty());
        positionColumn.setCellValueFactory(cellData -> cellData.getValue().getPositionProperty());
        loginColumn.setCellValueFactory(cellData -> cellData.getValue().getLoginProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().getPasswordProperty());
        mailColumn.setCellValueFactory(cellData -> cellData.getValue().getMailProperty());

        userTable.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                handleEditPersonButton();
            }
        });

    }

    private void showUserByDepartments(Department department) {
        if(department != null){
            ObservableList<User> userByDepartmentsList = FXCollections.observableArrayList();
            userByDepartmentsList.setAll(UserService.getUsersByDepartment(department));
            userTable.setItems(userByDepartmentsList);
        }
    }

    private void showDepartmentByOrganizationSelect(Organization organization){
        ObservableList<Department> departmentList = FXCollections.observableArrayList();
        departmentList.setAll(DepartmentService.getByOrganization(organization));
        departmentListView.setItems(departmentList);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleEditPersonButton() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            mainController.getDialogController().showUserEditDialog("Редактирование пользователя", selectedUser);
        }

    }

    @FXML
    private void handleNewUserButton() {
        User user = new User();
        mainController.getDialogController().showUserEditDialog("Добавить пользователя", user);
    }

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = userTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            User userToDelete = userTable.getSelectionModel().getSelectedItem();
            userTable.getItems().remove(selectedIndex);
            UserService.delete(userToDelete.getId());
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainController.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        }
    }

}
