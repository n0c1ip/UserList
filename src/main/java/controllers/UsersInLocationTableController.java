package controllers;

import crudDB.LocationService;
import crudDB.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.Location;
import objects.User;
import util.I18n;


public class UsersInLocationTableController {

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
        locationListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setUsersByLocationTable(newValue));

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

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void showAllLocations(){
        ObservableList<Location> locationList = FXCollections.observableArrayList();
        locationList.setAll(LocationService.getAll());
        locationListView.setItems(locationList);
    }

    public void setUsersByLocationTable(Location location) {
        ObservableList<User> userList = FXCollections.observableArrayList();
        userList.addAll(UserService.getUsersByLocation(location));

        //Wrap observableList in FilteredList
        FilteredList<User> filteredData = new FilteredList<>(userList, p -> true);
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
                return user.toString().toLowerCase().contains(lowerCaseFilter);
            });
        });

        //Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);
        usersCount.setText(I18n.TABLE.getString("Label.UserCount") + ": "
                +String.valueOf(tableView.getItems().size()));
    }

    @FXML
    private void handleNewUserButton() {
        mainController.getDialogController().showNewUserMethodChoiceDialog(locationListView.getSelectionModel().getSelectedItem());
        setUsersByLocationTable(locationListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            User userToDelete = tableView.getSelectionModel().getSelectedItem();
            tableView.getItems().remove(selectedIndex);
            UserService.delete(userToDelete.getId());
            setUsersByLocationTable(locationListView.getSelectionModel().getSelectedItem());
        } else {
            DialogController.showErrorDialog("Сначала выберите пользователя");
        }
    }

    @FXML
    private void handleEditPersonButton() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            mainController.getDialogController().showUserEditDialog(I18n.TABLE.getString("ContextMenu.EditUser"), selectedUser);
            setUsersByLocationTable(locationListView.getSelectionModel().getSelectedItem());
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

