package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import start.EntryPoint;
import util.I18n;

import java.io.IOException;

public class ClassificationEditDialogControllerTest extends GuiTest {

    @Override
    protected Parent getRootNode() {
        MainController mainController = new MainController();
        mainController.setPrimaryStage(GuiTest.stage);
        mainController.initDialogController();
        FXMLLoader loader = new FXMLLoader(EntryPoint.class.getResource("/fxml/classificationEditDialog.fxml"));
        loader.setResources(I18n.TABLE.getResourceBundle());
        AnchorPane table = new AnchorPane();

        try {
            table = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    @Test
    public void shouldHaveClassificationManagementButtons() throws Exception {
        Assert.assertNotNull(find("#okButton"));
        Assert.assertNotNull(find("#cancelButton"));
    }

    @Test
    public void shouldTypeInTextField() throws Exception {
        click("#nameField").type("a");
    }
}
