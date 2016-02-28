package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import util.Fxml;

import java.io.IOException;

import static junit.framework.Assert.assertNotNull;

public class LoginControllerTest extends GuiTest{

    @Override
    protected Parent getRootNode() {
        FXMLLoader loader = Fxml.getFXMLLoader("login.fxml");
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
        click("#fieldUserName").type("l");
        click("#fieldPassword").type("p");
        Assert.assertNotNull(find("#loginButton"));
        assertNotNull(GuiTest.find("#paneLogin"));
    }

}
