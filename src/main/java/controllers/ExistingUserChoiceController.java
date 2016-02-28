package controllers;

import crudDB.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import objects.Location;
import objects.User;
import util.I18n;


public class ExistingUserChoiceController {

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

    private Location location;

    public ExistingUserChoiceController() {

    }

    @FXML
    private void initialize() {

        tableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        showAllUsers();

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
                handleChoiceUserButton();
            }
        });

    }

    public void showAllUsers() {
        ObservableList<User> userByDepartmentsList = FXCollections.observableArrayList();
        userByDepartmentsList.setAll(UserService.getAll());
        tableView.setItems(userByDepartmentsList);
        usersCount.setText(I18n.TABLE.getString("Label.UserCount") + ": "
                                  +String.valueOf(tableView.getItems().size()));
    }

    @FXML
    private void handleChoiceUserButton() {
        ObservableList<User> users = tableView.getSelectionModel().getSelectedItems();
        for (User user : users) {
            user.setLocation(location);
            UserService.update(user);
        }
        closeWindow();
    }

    public void closeWindow(){
        Stage thisWindow = (Stage) tableView.getScene().getWindow();
        thisWindow.close();
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}

