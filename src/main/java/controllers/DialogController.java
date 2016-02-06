package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import objects.User;
import util.ListUtil;

import java.util.Optional;


/**
 * Contoller for Dialog windows
 */
public class DialogController {

    private Stage primaryStage;

    /**
     * Dialog shows when called adding or editing User
     * @param currentTable table which from called method(now dispalyed)
     * @param title Dialog title, depends (add\edit)
     * @param user editing User
     */
    public void showUserEditDialog(String currentTable, String title, User user) {
        try{
            final Stage dialog = new Stage();
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userEdit.fxml"));
            AnchorPane useredit = loader.load();
            UserEditController controller = loader.getController();
            controller.setDialog(dialog);
            controller.setEditedUser(user);
            controller.setCurrentTable(currentTable);
            dialog.setScene(new Scene(useredit));
            dialog.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * JavaFx TextInput Dialog creating new Department
     */
    public void showAddDepartmentDialog(){
        TextInputDialog dialog = new TextInputDialog("Название подразделения");
        dialog.setTitle("Создание подразделения");
        dialog.setHeaderText("Новое подразделение");
        dialog.setContentText("Введите название подразделения:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(ListUtil::createDepartment);
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
        Alert alert = new Alert(alertType);
        alert.setTitle(dialogTitle);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * Log in Dialog, shows before running app
     */
    public void showLoginDialog(){
        try{
            final Stage dialog = new Stage();
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            AnchorPane login = loader.load();
            LoginController controller = loader.getController();
            controller.setDialog(dialog);
            dialog.setScene(new Scene(login));
            dialog.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
