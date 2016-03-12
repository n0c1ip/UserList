package controllers;

import crudDB.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import objects.*;
import util.I18n;

import javax.persistence.NoResultException;

public class UserClassificationChoiceController {

    @FXML
    private ListView<Department> departmentListView;
    @FXML
    private ComboBox<Organization> organizationComboBox;
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> locationColumn;
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
    @FXML
    private TextField searchField;

    private Classification classification;

    @FXML
    private void initialize(){
        tableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        //Organization ComboBox
        ObservableList<Organization> organizationsList = FXCollections.observableArrayList();
        organizationsList.setAll(OrganizationService.getAll());
        organizationComboBox.setItems(organizationsList.sorted());
        organizationComboBox.getSelectionModel().selectFirst();
        showDepartmentByOrganizationSelect(organizationComboBox.getValue());
        organizationComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable1, oldValue, newValue ) -> showDepartmentByOrganizationSelect(newValue));

        //Departments ListView
        departmentListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showUserByDepartments(newValue));

        //User table
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().getDepartmentProperty());
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().getLocationProperty());
        middleNameColumn.setCellValueFactory(cellData -> cellData.getValue().getMiddleNameProperty());
        positionColumn.setCellValueFactory(cellData -> cellData.getValue().getPositionProperty());
        pcColumn.setCellValueFactory(cellData -> cellData.getValue().getPcProperty());
        loginColumn.setCellValueFactory(cellData -> cellData.getValue().getLoginProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().getPasswordProperty());
        mailColumn.setCellValueFactory(cellData -> cellData.getValue().getMailProperty());
    }

    private void showUserByDepartments(Department department) {
        if(department != null){
            ObservableList<User> userByDepartmentsList = FXCollections.observableArrayList();
            userByDepartmentsList.setAll(UserService.getUsersByDepartment(department));

            //Wrap observableList in FilteredList
            FilteredList<User> filteredData = new FilteredList<>(userByDepartmentsList, p -> true);
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
    }

    private void showDepartmentByOrganizationSelect(Organization organization){
        ObservableList<Department> departmentList = FXCollections.observableArrayList();
        departmentList.setAll(DepartmentService.getByOrganization(organization));
        departmentListView.setItems(departmentList);
    }

    public void handleChoiceButton() {
            ObservableList<User> users = tableView.getSelectionModel().getSelectedItems();
            for (User user : users) {
                if(!isAlreadyExistInClassification(user,classification)){
                    UserClassification userclassification = new UserClassification();
                    userclassification.setClassification(this.classification);
                    userclassification.setUser(user);
                    if (BeanValidation.isCorrectData(userclassification)) {
                        UserClassificationService.add(userclassification);
                    } else {
                        DialogController.showErrorDialog(BeanValidation.getViolationsText(userclassification));
                    }
                } else {
                    DialogController.showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Пользователь " +
                            user.getLastName() + " " + user.getFirstName() + " уже добавлен");
                }
            }
            closeWindow();
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    private boolean isAlreadyExistInClassification(User user, Classification classification){
        try{
            UserClassificationService.getByUserAndClassification(user,classification);
        } catch (NoResultException e){
            return false;
        }
        return true;
    }

    private void closeWindow(){
        Stage thisWindow = (Stage) tableView.getScene().getWindow();
        thisWindow.close();
    }
}
