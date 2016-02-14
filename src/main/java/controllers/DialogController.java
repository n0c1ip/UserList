package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import objects.User;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }


    /**
     * JavaFx TextInput Dialog creating new Department
     */
    public void showAddDepartmentDialog(){
        Optional<String> result = getDepartmentDialog().showAndWait();
    }
    public Dialog getDepartmentDialog(){
        TextInputDialog dialog = new TextInputDialog("Название подразделения");
        dialog.setTitle("Создание подразделения");
        dialog.setHeaderText("Новое подразделение");
        dialog.setContentText("Введите название подразделения:");
        return dialog;
    }


    public void showAddLocationDialog(){

    }

    /**
     * Shows modal window dialog, with OK button
     * @param alertType type of dialog
     * @param dialogTitle window title
     * @param contentText text inside window
     */
    public void showAlertDialog(Alert.AlertType alertType, String dialogTitle, String contentText){
        getAlertDialog(alertType, dialogTitle, contentText).showAndWait();
    }
    public Alert getAlertDialog(Alert.AlertType alertType, String dialogTitle, String contentText) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }
}
