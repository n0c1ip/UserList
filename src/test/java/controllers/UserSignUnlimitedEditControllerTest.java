package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import util.Fxml;
import util.I18n;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;


public class UserSignUnlimitedEditControllerTest extends GuiTest {

    @Override
    protected Parent getRootNode() {
        try {
            FXMLLoader rootLoader = Fxml.getFXMLLoader("userSignUnlimitedEdit.fxml");
            rootLoader.setResources(I18n.DIALOG.getResourceBundle());
            return rootLoader.<AnchorPane>load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldHaveAllElements() throws Exception {
        assertNotNull(GuiTest.find("#paneUserSignUnlimitedEdit"));
        assertNotNull(GuiTest.find("#okButton"));
        assertNotNull(GuiTest.find("#cancelButton"));
        click("#signBox").type(KeyCode.ESCAPE);
        click("#valueField").type("a");
    }

}
