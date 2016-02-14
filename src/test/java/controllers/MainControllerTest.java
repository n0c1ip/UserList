package controllers;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;


public class MainControllerTest extends GuiTest {

    private DialogController dialogController;
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    protected Parent getRootNode() {
        MainController mainController = new MainController();
        mainController.setPrimaryStage(GuiTest.stage);
        mainController.show();

        primaryStage = mainController.getPrimaryStage();
        dialogController = mainController.getDialogController();
        rootLayout = mainController.getRootLayout();

        return new BorderPane();
    }

    @Test
    public void mainControllerShouldBeInitialized() {
        Assert.assertNotNull(dialogController);
        Assert.assertEquals(stage, dialogController.getPrimaryStage());

        Assert.assertNotNull(primaryStage);
        Assert.assertNotNull(primaryStage.getScene());
        Assert.assertNotNull(primaryStage.getScene().getRoot());
        Assert.assertTrue(primaryStage.isShowing());

        Assert.assertNotNull(rootLayout);
        Assert.assertTrue(rootLayout.isCenterShape());
    }


}