package controllers;

import crudDB.ClassificationService;
import crudDB.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.Classification;
import objects.User;
import util.I18n;

public class ClassificationController {

    @FXML
    ListView<Classification> classificationListView;
    @FXML
    TableView<User> tableView;
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
    private ContextMenu classificationContextMenu;
    private MainController mainController;

    @FXML
    private void initialize() {

        classificationListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setUserByClassification(newValue));

        showAllClassifications();

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
        initiateClassificationContextMenu();

        classificationListView.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && classificationContextMenu.isShowing()){
                classificationContextMenu.hide();
            }
            if (event.isSecondaryButtonDown()) {
                classificationContextMenu.show(tableView,event.getScreenX(),event.getScreenY());
            }
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                handleEditClassification();
            }
        });
    }

    private void showAllClassifications(){
        ObservableList<Classification> classifications = FXCollections.observableArrayList();
        classifications.setAll(ClassificationService.getAll());
        classificationListView.setItems(classifications);
    }

    public void setUserByClassification(Classification classification) {
        ObservableList<User> userList = FXCollections.observableArrayList();
        userList.setAll(UserService.getUsersByClassification(classification));
        tableView.setItems(userList);
    }

    public void handleNewUserButton(ActionEvent actionEvent) {
    }

    public void handleDeletePerson(ActionEvent actionEvent) {
    }

    private void handelNewClassification() {
        Classification classification = new Classification();
        mainController.getDialogController().showClassificationEditDialog("Добавить классификатор", classification);
        showAllClassifications();
    }

    private void handleEditClassification() {

    }

    private void initiateClassificationContextMenu(){
        MenuItem addUser = new MenuItem(I18n.TABLE.getString("ContextMenu.AddUser"));
        MenuItem editUser = new MenuItem(I18n.TABLE.getString("ContextMenu.EditUser"));
        MenuItem removeUser = new MenuItem(I18n.TABLE.getString("ContextMenu.RemoveUser"));
        MenuItem showUnlimitedSigns = new MenuItem(I18n.TABLE.getString("ContextMenu.UserSign"));

        classificationContextMenu = new ContextMenu(addUser,editUser,removeUser,showUnlimitedSigns);

        addUser.setOnAction(event -> handelNewClassification());
//        editUser.setOnAction(event -> handleEditPersonButton());
//        removeUser.setOnAction(event -> handleDeletePerson());
//        showUnlimitedSigns.setOnAction(event -> showUserSignUnlimited(tableView.getSelectionModel().getSelectedItem()));
    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
