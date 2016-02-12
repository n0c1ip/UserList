package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;

public class RootControllerTest extends GuiTest {

    @Override
    protected Parent getRootNode() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/root.fxml"));
        BorderPane rootLayout = new BorderPane();
        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootLayout;
    }

    @Test
    public void rootLayoutShouldHaveMenuBar() throws Exception {
        MenuBar menuBar = GuiTest.find("#menuBar");
        assertNotNull(menuBar);
        assertTrue(menuBar.isVisible());

    }
}
