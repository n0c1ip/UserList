package controllers;

import crudDB.DepartmentService;
import crudDB.ExtendedRevisionService;
import crudDB.OrganizationService;
import crudDB.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.Department;
import objects.ExtendedRevisionEntity;
import objects.Organization;
import objects.User;
import util.ActiveUser;
import util.I18n;
import util.Permission;

public class UsersInDepartmentTableController {

    @FXML
    private Button addButton;
    @FXML
    private Button changeButton;
    @FXML
    private Button removeButton;
    private MainController mainController;
    @FXML
    private ListView<Department> departmentListView;
    @FXML
    private ComboBox<Organization> organizationComboBox;
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> locationColumn;
    @FXML
    private TableColumn<User, String> middleNameColumn;
    @FXML
    private TableColumn<User, String> departmentColumn;
    @FXML
    private TableColumn<User, String> positionColumn;
    @FXML
    private TableColumn<User, String> pcColumn;
    @FXML
    private TableColumn<User, String> loginColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> mailColumn;
    @FXML
    private Label usersCount;
    @FXML
    private TextField searchField;

    private ContextMenu userContextMenu;


    @FXML
    private void initialize(){
        if (ActiveUser.hasPermission(Permission.WRITE)) {
            //TableView context menu & double click
            initiateUserContextMenu();
            tableView.setOnMousePressed(event -> {
                if (event.isPrimaryButtonDown() && userContextMenu.isShowing()){
                    userContextMenu.hide();
                }
                if (event.isSecondaryButtonDown()) {
                    userContextMenu.show(tableView,event.getScreenX(),event.getScreenY());
                }
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    handleEditPersonButton();
                }
            });
        } else {
            addButton.setDisable(true);
            changeButton.setDisable(true);
            removeButton.setDisable(true);
        }

        //Organization ComboBox
        organizationComboBox.setItems(FXCollections.observableArrayList(OrganizationService.getAll()));
        organizationComboBox.getSelectionModel().selectFirst();
        showDepartmentByOrganizationSelect(organizationComboBox.getValue());
        organizationComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable1, oldValue, newValue ) -> showDepartmentByOrganizationSelect(newValue));

        //Departments ListView
        departmentListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showUserByDepartments(newValue, searchField.getText()));

        //User table
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().getDepartmentProperty());
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().getLocationProperty());
        middleNameColumn.setCellValueFactory(cellData -> cellData.getValue().getMiddleNameProperty());
        positionColumn.setCellValueFactory(cellData -> cellData.getValue().getPositionProperty());
        pcColumn.setCellValueFactory(cellData -> cellData.getValue().getPcProperty());
        loginColumn.setCellValueFactory(cellData -> cellData.getValue().getLoginProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().getPasswordProperty());
        mailColumn.setCellValueFactory(cellData -> cellData.getValue().getMailProperty());
    }

    private void showUserByDepartments(Department department, String searchValue) {

        if(department != null){
            Alert loadingAlert = DialogController.getAlertDialog(Alert.AlertType.INFORMATION, "", "Загрузка...");
            AsyncJavaFX.executeInNewThread(() -> {
                ObservableList<User> userByDepartmentsList = FXCollections.observableArrayList();
                userByDepartmentsList.setAll(UserService.getUsersByDepartment(department));

                //Wrap observableList in FilteredList
                FilteredList<User> filteredData = new FilteredList<>(userByDepartmentsList, p -> true);
                //Wrap FilteredList in SortedList
                SortedList<User> sortedData = new SortedList<>(filteredData);

                filteredData.setPredicate(user -> {
                    // If filter text is empty, display all users.
                    if (searchValue == null || searchValue.isEmpty()) {
                        return true;
                    }
                    //filter text
                    String lowerCaseFilter = searchValue.toLowerCase();
                    return user.toString().toLowerCase().contains(lowerCaseFilter);
                });

                //Bind the SortedList comparator to the TableView comparator.
                sortedData.comparatorProperty().bind(tableView.comparatorProperty());

                tableView.setItems(sortedData);
                usersCount.setText(I18n.TABLE.getString("Label.UserCount") + ": "
                        + String.valueOf(tableView.getItems().size()));

                loadingAlert.close();
            });
            loadingAlert.showAndWait();
        }
    }

    private void initiateUserContextMenu(){
        MenuItem addUser = new MenuItem(I18n.TABLE.getString("ContextMenu.AddUser"));
        MenuItem editUser = new MenuItem(I18n.TABLE.getString("ContextMenu.EditUser"));
        MenuItem removeUser = new MenuItem(I18n.TABLE.getString("ContextMenu.RemoveUser"));
        MenuItem showUnlimitedSigns = new MenuItem(I18n.TABLE.getString("ContextMenu.UserSign"));
        MenuItem lastEdit = new MenuItem(I18n.TABLE.getString("ContextMenu.LastEdit"));

        userContextMenu = new ContextMenu(addUser,editUser,removeUser,showUnlimitedSigns,lastEdit);

        addUser.setOnAction(event -> handleNewUserButton());
        editUser.setOnAction(event -> handleEditPersonButton());
        removeUser.setOnAction(event -> handleDeletePerson());
        showUnlimitedSigns.setOnAction(event -> showUserSignUnlimited(tableView.getSelectionModel().getSelectedItem()));
        lastEdit.setOnAction(event -> {
            User selectedUser = tableView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                ExtendedRevisionEntity revisionEntity = ExtendedRevisionService.getLastRevisionEntity(User.class, selectedUser);
                DialogController.showLastEditDialog(revisionEntity.getUserName(), revisionEntity.getRevisionDate());
            }
        });
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
    public void handleFilterButton() {
        showUserByDepartments(departmentListView.getSelectionModel().getSelectedItem(), searchField.getText());
    }

    @FXML
    private void handleEditPersonButton() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            mainController.getDialogController().showUserEditDialog(I18n.DIALOG.getString("Title.EditUser"), selectedUser);
            showUserByDepartments(departmentListView.getSelectionModel().getSelectedItem(), searchField.getText());
        }
    }

    @FXML
    private void handleNewUserButton() {
        User user = new User();
        mainController.getDialogController().showUserEditDialog(I18n.DIALOG.getString("Title.AddUser"), user);
        showUserByDepartments(departmentListView.getSelectionModel().getSelectedItem(), searchField.getText());
    }

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            User userToDelete = tableView.getSelectionModel().getSelectedItem();
            tableView.getItems().remove(selectedIndex,selectedIndex);
            UserService.delete(userToDelete.getId());
        } else {
            DialogController.showErrorDialog("Сначала выберите пользователя");
        }
    }

    private void showUserSignUnlimited(User user){
        mainController.getDialogController().showUserSignUnlimitedTableDialog("Signs",user);
    }


}
