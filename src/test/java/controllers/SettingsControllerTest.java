package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

public class SettingsControllerTest extends GuiTest{

    @Override
    protected Parent getRootNode() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/settings.fxml"));
            Pane settingsPane = loader.load();
            return settingsPane;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldHaveAllElements() throws Exception {
        Assert.assertNotNull(find("#settingsPane"));
        Assert.assertNotNull(find("#dbTypeComboBox"));
        Assert.assertNotNull(find("#serverTextField"));
        Assert.assertNotNull(find("#loginTextField"));
        Assert.assertNotNull(find("#passwordField"));
        Assert.assertNotNull(find("#passwordField"));
        Assert.assertNotNull(find("#testConnectionButton"));
        Assert.assertNotNull(find("#saveButton"));
        Assert.assertNotNull(find("#cancelButton"));
    }

    @Test
    public void shouldShowSettings() throws Exception {
        Pane userEdit = find("#settingsPane");
        Assert.assertTrue(userEdit.getScene().getWindow().isShowing());
    }
}
