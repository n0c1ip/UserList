package controllers;

import crudDB.LocationService;
import crudDB.UserService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.Location;
import objects.User;
import util.ActiveUser;
import util.I18n;
import util.Permission;

import javax.persistence.EntityManager;
import java.util.concurrent.CountDownLatch;


public class UsersInLocationTableController {

    @FXML
    private Button filterButton;
    @FXML
    private Button addButton;
    @FXML
    private Button changeButton;
    @FXML
    private Button removeButton;
    //Table
    @FXML
    private ListView<Location> locationListView;
    @FXML
    private TableView<User> tableView;
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
    private TableColumn<User, String> pcColumn;
    @FXML
    private TableColumn<User, String> loginColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> mailColumn;
    @FXML
    private TextField searchField;

    @FXML
    private Label usersCount;
    private ContextMenu userContextMenu;
    private MainController mainController;


    public UsersInLocationTableController() {

    }

    @FXML
    private void initialize() {
        if (ActiveUser.hasPermission(Permission.WRITE)) {
            //TableView context menu & double click
            initiateUserContextMenu();
            tableView.setOnMousePressed(event -> {
                if (event.isPrimaryButtonDown() && userContextMenu.isShowing()){
                    userContextMenu.hide();
                }
                if (event.isSecondaryButtonDown()) {
                    userContextMenu.show(tableView, event.getScreenX(),event.getScreenY());
                }
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    handleEditPersonButton();
                }
            });
        } else {
            filterButton.setDisable(true);
            addButton.setDisable(true);
            changeButton.setDisable(true);
            removeButton.setDisable(true);
        }
        locationListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setUsersByLocationTable(newValue, searchField.getText()));

        showAllLocations();

        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().getDepartmentProperty());
        middleNameColumn.setCellValueFactory(cellData -> cellData.getValue().getMiddleNameProperty());
        positionColumn.setCellValueFactory(cellData -> cellData.getValue().getPositionProperty());
        pcColumn.setCellValueFactory(cellData -> cellData.getValue().getPcProperty());
        loginColumn.setCellValueFactory(cellData -> cellData.getValue().getLoginProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().getPasswordProperty());
        mailColumn.setCellValueFactory(cellData -> cellData.getValue().getMailProperty());
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void showAllLocations(){
        ObservableList<Location> locationList = FXCollections.observableArrayList();
        locationList.setAll(LocationService.getAll());
        locationListView.setItems(locationList);
    }

    public void setUsersByLocationTable(Location location, String searchValue) {
        Alert loadingAlert = DialogController.getAlertDialog(Alert.AlertType.INFORMATION, "", "Загрузка...");

        AsyncJavaFX.executeInNewThread(() -> {
            ObservableList<User> userList = FXCollections.observableArrayList();
            userList.addAll(UserService.getUsersByLocation(location));

            //Wrap observableList in FilteredList
            FilteredList<User> filteredData = new FilteredList<>(userList, p -> true);
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

    @FXML
    public void handleFilterButton() {
        setUsersByLocationTable(locationListView.getSelectionModel().getSelectedItem(), searchField.getText());
    }

    @FXML
    private void handleNewUserButton() {
        mainController.getDialogController().showNewUserMethodChoiceDialog(locationListView.getSelectionModel().getSelectedItem());
        setUsersByLocationTable(locationListView.getSelectionModel().getSelectedItem(), searchField.getText());
    }

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            User userToDelete = tableView.getSelectionModel().getSelectedItem();
            tableView.getItems().remove(selectedIndex);
            UserService.delete(userToDelete.getId());
            setUsersByLocationTable(locationListView.getSelectionModel().getSelectedItem(), searchField.getText());
        } else {
            DialogController.showErrorDialog("Сначала выберите пользователя");
        }
    }

    @FXML
    private void handleEditPersonButton() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            mainController.getDialogController().showUserEditDialog(I18n.TABLE.getString("ContextMenu.EditUser"), selectedUser);
            setUsersByLocationTable(locationListView.getSelectionModel().getSelectedItem(), searchField.getText());
        }
    }

    private void showUserSignUnlimited(User user){
        mainController.getDialogController().showUserSignUnlimitedTableDialog("Signs",user);
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

}

