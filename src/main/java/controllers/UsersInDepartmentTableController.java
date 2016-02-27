package controllers;

import crudDB.DepartmentService;
import crudDB.OrganizationService;
import crudDB.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.Department;
import objects.Organization;
import objects.User;
import util.I18n;

public class UsersInDepartmentTableController {

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
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().getLocationProperty());
        middleNameColumn.setCellValueFactory(cellData -> cellData.getValue().getMiddleNameProperty());
        positionColumn.setCellValueFactory(cellData -> cellData.getValue().getPositionProperty());
        pcColumn.setCellValueFactory(cellData -> cellData.getValue().getPcProperty());
        loginColumn.setCellValueFactory(cellData -> cellData.getValue().getLoginProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().getPasswordProperty());
        mailColumn.setCellValueFactory(cellData -> cellData.getValue().getMailProperty());

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
    }

    private void showUserByDepartments(Department department) {
        if(department != null){
            ObservableList<User> userByDepartmentsList = FXCollections.observableArrayList();
            userByDepartmentsList.setAll(UserService.getUsersByDepartment(department));

            //Wrap observableList in FilteredList
            FilteredList<User> filteredData = new FilteredList<>(userByDepartmentsList, p -> true);
            //Wrap FilteredList in SortedList
            SortedList<User> sortedData = new SortedList<>(filteredData);

            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(user -> {
                    // If filter text is empty, display all users.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    //filter text
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (user.toString().toLowerCase().contains(lowerCaseFilter)){
                        return true; // Filter matches users fields.
                    }
                    return false; // Does not match.
                });
            });

            //Bind the SortedList comparator to the TableView comparator.
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());

            tableView.setItems(sortedData);
            usersCount.setText(I18n.TABLE.getString("Label.UserCount") + ": "
                                         +String.valueOf(tableView.getItems().size()));
        }
    }

    private void initiateUserContextMenu(){
        MenuItem addUser = new MenuItem(I18n.TABLE.getString("ContextMenu.AddUser"));
        MenuItem editUser = new MenuItem(I18n.TABLE.getString("ContextMenu.EditUser"));
        MenuItem removeUser = new MenuItem(I18n.TABLE.getString("ContextMenu.RemoveUser"));
        MenuItem showUnlimitedSigns = new MenuItem(I18n.TABLE.getString("ContextMenu.UserSign"));

        userContextMenu = new ContextMenu(addUser,editUser,removeUser,showUnlimitedSigns);

        addUser.setOnAction(event -> handleNewUserButton());
        editUser.setOnAction(event -> handleEditPersonButton());
        removeUser.setOnAction(event -> handleDeletePerson());
        showUnlimitedSigns.setOnAction(event -> showUserSignUnlimited(tableView.getSelectionModel().getSelectedItem()));
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
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            mainController.getDialogController().showUserEditDialog(I18n.DIALOG.getString("Title.EditUser"), selectedUser);
        }
    }
    @FXML
    private void handleNewUserButton() {
        User user = new User();
        mainController.getDialogController().showUserEditDialog(I18n.DIALOG.getString("Title.AddUser"), user);
    }
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            User userToDelete = tableView.getSelectionModel().getSelectedItem();
            tableView.getItems().remove(selectedIndex);
            UserService.delete(userToDelete.getId());
        } else {
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Не выбран пользователь", "Сначала выберите пользователя");
        }
    }

    private void showUserSignUnlimited(User user){
        mainController.getDialogController().showUserSignUnlimitedTableDialog("Signs",user);
    }

}
