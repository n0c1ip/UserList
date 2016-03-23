package controllers;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import objects.Location;
import objects.SignUnlimited;
import objects.User;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;


public class DialogControllerTest extends GuiTest {

    private Stage userEditDialog;
    private String userTitleText= "Тест userEdit";

    private Stage signUnlimitedDialog;
    private String signUnlimitedDialogTitleText = "Редактирование признака";

    private Stage userChoiceDialog;
    private String userChoiceDialogTitleText = "Выбор пользователя из списка";

    private Dialog alertDialog;
    private String alertTitleText = "Тест alert";

    private Stage loginDialog;

    @Override
    protected Parent getRootNode() {
        MainController mainController = new MainController();
        mainController.setPrimaryStage(GuiTest.stage);
        mainController.show();

        DialogController dialogController = mainController.getDialogController();
        alertDialog = DialogController.getAlertDialog(Alert.AlertType.INFORMATION, alertTitleText, "");
        userEditDialog = dialogController.getUserEditDialog(userTitleText, new User());
        userChoiceDialog = dialogController.getExistingUserChoiceDialog(userChoiceDialogTitleText, new Location());
        loginDialog = dialogController.getLoginDialog();
        signUnlimitedDialog = dialogController.getSignUnlimitedEditDialog(signUnlimitedDialogTitleText, new SignUnlimited());

        return new BorderPane();
    }

    @Test
    public void userEditDialogShouldBeCorrect() {
        Assert.assertNotNull(userEditDialog);
        Assert.assertEquals(userTitleText, userEditDialog.getTitle());
        Assert.assertNotNull(userEditDialog.getOwner());
        Assert.assertEquals(GuiTest.stage, userEditDialog.getOwner());
        Assert.assertNotNull(userEditDialog.getScene().getRoot());
    }

    @Test
    public void alertDialogShouldBeCorrect() {
        Assert.assertNotNull(alertDialog);
        Assert.assertTrue(alertDialog instanceof Alert);
        Assert.assertEquals(alertTitleText, alertDialog.getTitle());
    }

    @Test
    public void userChoiceDialogShouldBeCorrect() {
        Assert.assertNotNull(userChoiceDialog);
        Assert.assertEquals(userChoiceDialogTitleText, userChoiceDialog.getTitle());
    }

    @Test
    public void SignUnlimitedEditDialogShouldBeCorrect() {
        Assert.assertNotNull(signUnlimitedDialog);
        Assert.assertEquals(GuiTest.stage, signUnlimitedDialog.getOwner());
        Assert.assertEquals(signUnlimitedDialogTitleText, signUnlimitedDialog.getTitle());
    }


}

