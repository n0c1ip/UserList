package controllers;

import crudDB.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.Location;
import objects.User;


public class TableController {

    private static final String CREATE_TITLE = "Создание пользователя";
    private static final String EDIT_TITLE = "Редактирование пользователя";

    //Table
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


    //User preview
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label middleNameLabel;
    @FXML
    private Label departmentLabel;
    @FXML
    private Label positionLabel;
    @FXML
    private Label pcNameLabel;
    @FXML
    private Label loginLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label mailLabel;

    //Location preview
    @FXML
    private Label locationNameLabel;
    @FXML
    private Label locationAddressLabel;
    @FXML
    private Label locationUserCounterLabel;


    private MainController mainController;


    public TableController() {

    }

    @FXML
    private void initialize() {

        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().getDepartmentProperty());
        middleNameColumn.setCellValueFactory(cellData -> cellData.getValue().getMiddleNameProperty());
        positionColumn.setCellValueFactory(cellData -> cellData.getValue().getPositionProperty());
        pcColumn.setCellValueFactory(cellData -> cellData.getValue().getPcProperty());
        loginColumn.setCellValueFactory(cellData -> cellData.getValue().getLoginProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().getPasswordProperty());
        mailColumn.setCellValueFactory(cellData -> cellData.getValue().getMailProperty());

        showUserDetails(null);

        //change user listener
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showUserDetails(newValue));

        //Double click edit user
        tableView.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                handleEditPerson();
            }
        });



    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setUserLocationTable(Location location) {
        ObservableList<User> tableViewList = FXCollections.observableArrayList();
        tableViewList.addAll(UserService.getUsersByLocation(location));
        tableView.setItems(tableViewList);

        locationNameLabel.setText(location.getName());
        locationAddressLabel.setText(location.getAddress());
        locationUserCounterLabel.setText(Integer.toString(tableViewList.size()));
    }

    private void showUserDetails(User user) {
        if (user != null) {
            firstNameLabel.setText(user.getFirstName());
            lastNameLabel.setText(user.getLastName());
            middleNameLabel.setText(user.getMiddleName());
            departmentLabel.setText(user.getDepartment().getName());
            positionLabel.setText(user.getPosition());
            loginLabel.setText(user.getLogin());
            passwordLabel.setText(user.getPassword());
            mailLabel.setText(user.getMail());
        } else {

            firstNameLabel.setText("");
            lastNameLabel.setText("");
            middleNameLabel.setText("");
            departmentLabel.setText("");
            positionLabel.setText("");
            pcColumn.setText("");
            loginLabel.setText("");
            passwordLabel.setText("");
            mailLabel.setText("");
        }
    }

    @FXML
    private void handleNewUserButton(ActionEvent actionEvent) {
        User user = new User();
        mainController.getDialogController().showUserEditDialog(CREATE_TITLE, user);
    }

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            User userToDelete = tableView.getSelectionModel().getSelectedItem();
            tableView.getItems().remove(selectedIndex);
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

    @FXML
    private void handleEditPerson() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            mainController.getDialogController().showUserEditDialog(EDIT_TITLE, selectedUser);
        }

    }

}

