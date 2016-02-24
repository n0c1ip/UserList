package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

import static junit.framework.Assert.assertNotNull;

public class UserMethodChoiceTest extends GuiTest{

    @Override
    protected Parent getRootNode() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userMethodChoiceDialog.fxml"));
        AnchorPane login = new AnchorPane();
        try {
            login = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return login;
    }

    @Test
    public void loginShouldHaveAllElements() throws Exception {
        assertNotNull(GuiTest.find("#paneUserMethodChoice"));
        assertNotNull(GuiTest.find("#newUserButton"));
        assertNotNull(GuiTest.find("#existingUserButton"));
    }

}