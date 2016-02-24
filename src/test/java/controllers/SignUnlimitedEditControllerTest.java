package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;


public class SignUnlimitedEditControllerTest extends GuiTest {

    @Override
    protected Parent getRootNode() {
        try {
            FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/fxml/signUnlimitedEditDialog.fxml"));
            AnchorPane rootLayout = rootLoader.load();
            return rootLayout;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldHaveAllElements() throws Exception {
        assertNotNull(GuiTest.find("#paneSignUnlimitedEdit"));
        assertNotNull(GuiTest.find("#okButton"));
        assertNotNull(GuiTest.find("#cancelButton"));
        click("#nameField").type("a");
    }

}