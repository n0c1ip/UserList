package controllers;

import crudDB.ClassificationService;
import crudDB.UserClassificationService;
import crudDB.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.Classification;
import objects.User;
import objects.UserClassification;
import util.I18n;

import javax.persistence.RollbackException;

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

        tableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

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

    public void handleAddUserToClassificationButton() {
        Classification classification = classificationListView.getSelectionModel().getSelectedItem();
        if(classification != null){
            mainController.getDialogController().showExistingUserInDepartmentChoiceDialog(I18n.DIALOG.getString("Title.AddUsers"), classification);
            setUserByClassification(classification);
        } else {
            DialogController.showAlertDialog(Alert.AlertType.ERROR,"Ошибка","Необходимо выбрать классификатор");
        }
    }

    public void handleDeletePersonFromClassification() {
        ObservableList<User> userList = tableView.getSelectionModel().getSelectedItems();
        Classification classification = classificationListView.getSelectionModel().getSelectedItem();
        if(!userList.isEmpty()){
            for (User user : userList) {
                UserClassification us = UserClassificationService.getByUserAndClassification(user,classification);
                UserClassificationService.delete(us.getId());
            }
            setUserByClassification(classification);
        } else {
            DialogController.showAlertDialog(Alert.AlertType.ERROR,"Ошибка","Необходимо выбрать пользователя");
        }

    }

    private void handelNewClassification() {
        Classification classification = new Classification();
        mainController.getDialogController().showClassificationEditDialog("Добавить классификатор", classification);
        showAllClassifications();
    }

    private void handleEditClassification() {
        Classification classification = classificationListView.getSelectionModel().getSelectedItem();
        mainController.getDialogController().showClassificationEditDialog("Изменить классификатор", classification);
        showAllClassifications();
    }

    private void handleRemoveClassification() {
        int selectedIndex = classificationListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Classification classificationToDelete = classificationListView.getSelectionModel().getSelectedItem();
            try{
                ClassificationService.delete(classificationToDelete.getId());
                classificationListView.getItems().remove(selectedIndex);
            }catch (RollbackException e){
                DialogController.showAlertDialog(Alert.AlertType.ERROR, "Удалиение классификатора", "Нельзя удалить классификатор с пользователями");
            }
        } else {
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Не выбран пользователь", "Сначала выберите пользователя");
        }
    }

    private void initiateClassificationContextMenu(){
        MenuItem addClassification = new MenuItem(I18n.TABLE.getString("ContextMenu.AddClassification"));
        MenuItem editClassification = new MenuItem(I18n.TABLE.getString("ContextMenu.EditClassification"));
        MenuItem removeClassification = new MenuItem(I18n.TABLE.getString("ContextMenu.RemoveClassification"));

        classificationContextMenu = new ContextMenu(addClassification,editClassification,removeClassification);

        addClassification.setOnAction(event -> handelNewClassification());
        editClassification.setOnAction(event -> handleEditClassification());
        removeClassification.setOnAction(event -> handleRemoveClassification());
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
