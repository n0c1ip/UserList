package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import objects.Department;
import objects.Location;
import objects.Organization;
import objects.User;
import util.I18n;
import objects.*;

import java.io.File;
import java.io.IOException;


/**
 * Controller for Dialog windows
 */
public class DialogController {

    private Stage primaryStage;


    public void showExistingUserChoiceDialog(Location location) {
        getExistingUserChoiceDialog(location).showAndWait();
    }
    public Stage getExistingUserChoiceDialog(Location location) {
        final Stage dialog = new Stage();
        try {
            dialog.setTitle(I18n.TABLE.getString("Title.ExistingUserChoice"));
            dialog.getIcons().add(new Image("icons/User-icon.png"));
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/existingUserChoiceTable.fxml"));
            loader.setResources(I18n.TABLE.getBundle());
            SplitPane useredit = loader.load();
            ExistingUserChoiceController controller = loader.getController();
            controller.setLocation(location);
            dialog.setScene(new Scene(useredit));
            return dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс выбора способа добавления пользователя");
        }
        return dialog;
    }

    public void showNewUserMethodChoiceDialog(Location location) {
        getNewUserMethodChoiceDialog(location).showAndWait();
    }
    public Stage getNewUserMethodChoiceDialog(Location location) {
        final Stage dialog = new Stage();
        try{
            dialog.setTitle(I18n.DIALOG.getString("Title.UserMethodAdd"));
            dialog.getIcons().add(new Image("icons/User-icon.png"));
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userMethodChoiceDialog.fxml"));
            loader.setResources(I18n.DIALOG.getBundle());
            AnchorPane useredit = loader.load();
            UserMethodChoiceController controller = loader.getController();
            controller.setLocation(location);
            controller.setDialogController(this);
            dialog.setScene(new Scene(useredit));
            return  dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс выбора способа добавления пользователя");
        }
        return dialog;
    }

    /**
     * Dialog shows when called adding or editing User
     * @param title Dialog title, depends (add\edit)
     * @param user editing User
     */
    public void showUserEditDialog(String title, User user) {
        getUserEditDialog(title, user).showAndWait();
    }
    public Stage getUserEditDialog(String title, User user) {
        final Stage dialog = new Stage();
        try{
            dialog.setTitle(title);
            dialog.getIcons().add(new Image("icons/User-icon.png"));
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userEditDialog.fxml"));
            loader.setResources(I18n.DIALOG.getBundle());
            AnchorPane useredit = loader.load();
            UserEditController controller = loader.getController();
            controller.setEditedUser(user);
            dialog.setScene(new Scene(useredit));
            return  dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс редактирования пользователя");
        }
        return dialog;
    }

    public void showSignUnlimitedEditDialog(String title, SignUnlimited signUnlimited) {
        getSignUnlimitedEditDialog(title, signUnlimited).showAndWait();
    }
    public Stage getSignUnlimitedEditDialog(String title, SignUnlimited signUnlimited) {
        final Stage dialog = new Stage();
        try{
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signUnlimitedEditDialog.fxml"));
            AnchorPane useredit = loader.load();
            SignUnlimitedEditController controller = loader.getController();
            controller.setEditedSignUnlimited(signUnlimited);
            dialog.setScene(new Scene(useredit));
            return  dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс редактирования признака");
        }
        return dialog;
    }


    public void showOrganizationEditDialog(String title, Organization organization) {
        getOrganizationEditDialog(title, organization).showAndWait();
    }
    public Stage getOrganizationEditDialog(String title, Organization organization) {
        final Stage dialog = new Stage();
        try{
            dialog.setTitle(title);
            dialog.getIcons().add(new Image("icons/organization-icon.png"));
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/organizationEditDialog.fxml"));
            loader.setResources(I18n.DIALOG.getBundle());
            AnchorPane useredit = loader.load();
            OrganizationEditController controller = loader.getController();
            controller.setEditedOrganization(organization);
            dialog.setScene(new Scene(useredit));
            return  dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс редактирования организации");
        }
        return dialog;
    }

    public void showDepartmentEditDialog(String title, Department department, Organization organization) {
        getDepartmentEditDialog(title, department, organization).showAndWait();
    }
    public Stage getDepartmentEditDialog(String title, Department department, Organization organization) {
        final Stage dialog = new Stage();
        dialog.getIcons().add(new Image("icons/department-icon.png"));
        try{
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/departmentEditDialog.fxml"));
            loader.setResources(I18n.DIALOG.getBundle());
            AnchorPane useredit = loader.load();
            DepartmentEditController controller = loader.getController();
            controller.setActiveOrganization(organization);
            controller.setEditedDepartment(department);
            dialog.setScene(new Scene(useredit));
            return  dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс редактирования пользователя");
        }
        return dialog;
    }

    public void showLocationEditDialog(String title, Location location){
        getLocationEditDialog(title,location).showAndWait();
    }
    public Stage getLocationEditDialog(String title, Location location) {
        final Stage dialog = new Stage();
        dialog.getIcons().add(new Image("icons/location-icon.png"));
        try{
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/locationEditDialog.fxml"));
            loader.setResources(I18n.DIALOG.getBundle());
            AnchorPane locationEdit = loader.load();
            LocationEditController controller = loader.getController();
            controller.setEditedLocation(location);
            dialog.setScene(new Scene(locationEdit));
            return dialog;
        } catch (IOException e){
            System.out.println(e.getMessage());
            showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс редактирования объектов");
        }
        return dialog;
    }

    public Dialog getObjectDialog(String objectName){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Создание " + objectName);
        dialog.setHeaderText("Создание " + objectName);
        dialog.setContentText("Введите название " + objectName + ":");
        return dialog;
    }

    /**
     * Shows modal window dialog, with OK button
     * @param alertType type of dialog
     * @param dialogTitle window title
     * @param contentText text inside window
     */
    public static void showAlertDialog(Alert.AlertType alertType, String dialogTitle, String contentText){
        getAlertDialog(alertType, dialogTitle, contentText).showAndWait();
    }
    public static Alert getAlertDialog(Alert.AlertType alertType, String dialogTitle, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(dialogTitle);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        return alert;
    }

    /**
     * Log in Dialog, shows before running app
     */
    public void showLoginDialog(){
        getLoginDialog().showAndWait();
    }
    public Stage getLoginDialog(){
        final Stage dialog = new Stage();
        try{
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            AnchorPane login = loader.load();
            LoginController controller = loader.getController();
            dialog.setScene(new Scene(login));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс логина");
        }
        return dialog;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public File showFileSaveDialog(String descriptionExtension, String extension){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(descriptionExtension,extension);
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showSaveDialog(primaryStage);
    }
}
