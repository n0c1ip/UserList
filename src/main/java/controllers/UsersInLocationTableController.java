package controllers;

import crudDB.LocationService;
import crudDB.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.Location;
import objects.User;


public class UsersInLocationTableController {

    private static final String CREATE_TITLE = "Создание пользователя";
    private static final String EDIT_TITLE = "Редактирование пользователя";

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
    private Label usersCount;


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


        //Double click edit user
        tableView.setOnMousePressed(event -> {
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
        ObservableList<User> tableViewList = FXCollections.observableArrayList();
        tableViewList.addAll(UserService.getUsersByLocation(location));
        tableView.setItems(tableViewList);

        usersCount.setText(Integer.toString(tableViewList.size()));
    }

    @FXML
    private void handleNewUserButton(ActionEvent actionEvent) {
        User user = new User();
        mainController.getDialogController().showUserEditDialog(CREATE_TITLE, user);
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
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainController.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        }

    }

    @FXML
    private void handleEditPersonButton() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            mainController.getDialogController().showUserEditDialog(EDIT_TITLE, selectedUser);
            setUsersByLocationTable(locationListView.getSelectionModel().getSelectedItem());
        }

    }

}

