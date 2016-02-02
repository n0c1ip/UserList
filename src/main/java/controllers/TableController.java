package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import objects.User;
import start.EnterPoint;
import util.ListUtil;


public class TableController {

    private String currentTablename;
    private static final String CREATE_TITLE = "Создание пользователя";

    public static String getCreateTitle() {
        return CREATE_TITLE;
    }

    public static String getEditTitle() {
        return EDIT_TITLE;
    }

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


    private EnterPoint enterPoint;


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
        userTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    handleEditPerson();
                }
            }
        });

        //TODO Editable Cells
    }


    public void setEnterPoint(EnterPoint enterPoint) {
        this.enterPoint = enterPoint;
    }

    public void setUserTable(String tableName) {
        userTable.setItems(ListUtil.getListByName(tableName));
    }

    public void setCurrentTablename(String currentTablename) {
        this.currentTablename = currentTablename;
    }

    public String getCurrentTablename() {
        return currentTablename;
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
        enterPoint.showUserEditDialog(currentTablename, CREATE_TITLE, user);
    }

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = userTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            userTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(enterPoint.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditPerson() {
        User selecteduser = userTable.getSelectionModel().getSelectedItem();
        if (selecteduser != null) {
            enterPoint.showUserEditDialog(currentTablename, EDIT_TITLE, selecteduser);
        }

    }

}

