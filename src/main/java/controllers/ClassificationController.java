package controllers;

import crudDB.ClassificationService;
import crudDB.ExtendedRevisionService;
import crudDB.UserClassificationService;
import crudDB.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.Classification;
import objects.ExtendedRevisionEntity;
import objects.User;
import objects.UserClassification;
import util.ActiveUser;
import util.I18n;
import util.Permission;

import javax.persistence.RollbackException;

public class ClassificationController {

    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private ListView<Classification> classificationListView;
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
    private ContextMenu classificationContextMenu;
    private MainController mainController;

    @FXML
    private void initialize() {
        if (ActiveUser.hasPermission(Permission.WRITE)) {
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
        } else {
            addButton.setDisable(true);
            removeButton.setDisable(true);
        }
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
    }

    private void showAllClassifications(){
        classificationListView.setItems(FXCollections.observableArrayList(ClassificationService.getAll()));
    }

    private void setUserByClassification(Classification classification) {
        tableView.setItems(FXCollections.observableArrayList(UserService.getUsersByClassification(classification)));
        usersCount.setText(I18n.TABLE.getString("Label.UserCount") + ": "
                +String.valueOf(tableView.getItems().size()));
    }

    public void handleAddUserToClassificationButton() {
        Classification classification = classificationListView.getSelectionModel().getSelectedItem();
        if(classification != null){
            mainController.getDialogController().showExistingUserInDepartmentChoiceDialog(I18n.DIALOG.getString("Title.AddUsers"), classification);
            setUserByClassification(classification);
        } else {
            DialogController.showErrorDialog("Необходимо выбрать классификатор");
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
            DialogController.showErrorDialog("Необходимо выбрать пользователя");
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
                DialogController.showErrorDialog("Нельзя удалить классификатор с пользователями");
            }
        } else {
            DialogController.showErrorDialog("Сначала выберите пользователя");
        }
    }

    private void initiateClassificationContextMenu(){
        MenuItem addClassification = new MenuItem(I18n.TABLE.getString("ContextMenu.AddClassification"));
        MenuItem editClassification = new MenuItem(I18n.TABLE.getString("ContextMenu.EditClassification"));
        MenuItem removeClassification = new MenuItem(I18n.TABLE.getString("ContextMenu.RemoveClassification"));
        MenuItem lastEdit = new MenuItem(I18n.TABLE.getString("ContextMenu.LastEdit"));

        classificationContextMenu = new ContextMenu(addClassification,editClassification,removeClassification,lastEdit);

        addClassification.setOnAction(event -> handelNewClassification());
        editClassification.setOnAction(event -> handleEditClassification());
        removeClassification.setOnAction(event -> handleRemoveClassification());
        lastEdit.setOnAction(event -> {
            Classification selectedClassification = classificationListView.getSelectionModel().getSelectedItem();
            if (selectedClassification != null) {
                ExtendedRevisionEntity revisionEntity = ExtendedRevisionService.getLastRevisionEntity(Classification.class, selectedClassification);
                DialogController.showLastEditDialog(revisionEntity.getUserName(), revisionEntity.getRevisionDate());
            }
        });
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
