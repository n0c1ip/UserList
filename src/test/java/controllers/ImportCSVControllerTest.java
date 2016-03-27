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

import static junit.framework.Assert.assertNotNull;

public class ImportCSVControllerTest extends GuiTest{

    @Override
    protected Parent getRootNode() {
        FXMLLoader loader = Fxml.getFXMLLoader("importCSV.fxml");
        loader.setResources(I18n.DIALOG.getResourceBundle());
        AnchorPane pane = new AnchorPane();
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    @Test
    public void shouldHaveAllElements() throws Exception {
        assertNotNull(GuiTest.find("#choiceBox"));
        assertNotNull(GuiTest.find("#filePathField"));
        assertNotNull(GuiTest.find("#choiceLoad"));
        assertNotNull(GuiTest.find("#choiceCancel"));
        click("#choiceBox").push(KeyCode.ESCAPE);
    }

}
