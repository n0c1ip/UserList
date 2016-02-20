package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import objects.Department;
import objects.Organization;
import objects.User;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


/**
 * Contoller for Dialog windows
 */
public class DialogController {

    private Stage primaryStage;

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
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userEditDialog.fxml"));
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

    public void showDepartmentEditDialog(String title, Department department, Organization organization) {
        getDepartmentEditDialog(title, department, organization).showAndWait();
    }
    public Stage getDepartmentEditDialog(String title, Department department, Organization organization) {
        final Stage dialog = new Stage();
        try{
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/departmentEditDialog.fxml"));
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


    public Optional<String> showAddLocationDialog(){
        Optional<String> result = getObjectDialog("объекта").showAndWait();
        return result;
    }

    public Optional<String> showAddOrganizationDialog(){
        Optional<String> result = getObjectDialog("организации").showAndWait();
        return result;
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

    public File showFileSaveDialog(String descriptionExtention, String extension){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(descriptionExtention,extension);
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showSaveDialog(primaryStage);
    }
}
