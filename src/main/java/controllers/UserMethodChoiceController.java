package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import objects.Location;
import objects.User;
import util.I18n;

public class UserMethodChoiceController {

    @FXML
    private Button newUserButton;
    @FXML
    private Button existingUserButton;

    private Location location;
    private DialogController dialogController;

    public void handleNewUserButton() {
        User user = new User();
        dialogController.showUserEditDialog("Создание пользователя", user);
        closeWindow();
    }

    public void handleExistingUserButton() {
        dialogController.showExistingUserChoiceDialog(I18n.TABLE.getString("Title.ExistingUserChoice"), location);
        closeWindow();
    }

    public void closeWindow(){
        Stage thisWindow = (Stage) newUserButton.getScene().getWindow();
        thisWindow.close();
    }

    public void setDialogController(DialogController dialogController) {
        this.dialogController = dialogController;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
