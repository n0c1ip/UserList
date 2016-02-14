package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

import static junit.framework.Assert.assertNotNull;

public class DialogControllerTest extends GuiTest{

    @Override
    protected Parent getRootNode() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));

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
        assertNotNull(GuiTest.find("#paneLogin"));
        click("#fieldUserName").type("log");
        click("#fieldPassword").type("pass");
//      click("#loginButton");
        assertNotNull(GuiTest.find("#paneLogin"));
    }

}
