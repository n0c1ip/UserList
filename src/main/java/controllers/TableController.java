package controllers;

import crud.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import objects.Location;
import objects.User;


public class TableController {

    private static final String CREATE_TITLE = "Создание пользователя";
    private static final String EDIT_TITLE = "Редактирование пользователя";

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
    private Label loginLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label mailLabel;


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
        loginColumn.setCellValueFactory(cellData -> cellData.getValue().getLoginProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().getPasswordProperty());
        mailColumn.setCellValueFactory(cellData -> cellData.getValue().getMailProperty());

        showUserDetails(null);

        //change user listener
        userTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showUserDetails(newValue));

        //Double click edit user
        userTable.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                handleEditPerson();
            }
        });

        //TODO Editable Cells
    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setUserLocationTable(Location locationName) {
        ObservableList<User> tableViewList = FXCollections.observableArrayList();

        tableViewList.addAll(UserService.getUsersByLocation(locationName));
        userTable.setItems(tableViewList);
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

    @FXML
    private void handleEditPerson() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            mainController.getDialogController().showUserEditDialog(EDIT_TITLE, selectedUser);
        }

    }

}

