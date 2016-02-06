package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import objects.User;
import util.ListUtil;

public class DepartmentTableController {

    private MainController enterPoint;
    @FXML
    private ListView<String> departmentListView;

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
    private void initialize(){

        //Departments ListView
        ObservableList<String> departmentList = FXCollections.observableArrayList();
        departmentList.setAll(ListUtil.getDepartmentsStrings());
        departmentListView.setItems(departmentList);

        departmentListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showUserByDepartments(newValue));

        //User table
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().getDepartmentProperty());
        middleNameColumn.setCellValueFactory(cellData -> cellData.getValue().getMiddleNameProperty());
        positionColumn.setCellValueFactory(cellData -> cellData.getValue().getPositionProperty());
        loginColumn.setCellValueFactory(cellData -> cellData.getValue().getLoginProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().getPasswordProperty());
        mailColumn.setCellValueFactory(cellData -> cellData.getValue().getMailProperty());

    }

    private void showUserByDepartments(String departmentName) {
//        if(departmentName != null){
//            ObservableList<User> userByDepartmentsList = FXCollections.observableArrayList();
//            userByDepartmentsList.setAll(ListUtil.getDepartmentByName(departmentName).getUserList());
//            userTable.setItems(userByDepartmentsList);
//        }
    }

    public void setMainController(MainController mainController) {
        this.enterPoint = mainController;
    }

    public void handleUserAddButton() {


    }

}
