package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import util.I18n;

import java.io.IOException;

public class SettingsControllerTest extends GuiTest{

    @Override
    protected Parent getRootNode() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/settings.fxml"));
            loader.setResources(I18n.DIALOG.getResourceBundle());
            Pane settingsPane = loader.load();
            return settingsPane;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldHaveAllElements() throws Exception {
        Assert.assertNotNull(find("#languageComboBox"));
        Assert.assertNotNull(find("#settingsPane"));
        Assert.assertNotNull(find("#dbTypeComboBox"));
        Assert.assertNotNull(find("#serverTextField"));
        Assert.assertNotNull(find("#loginTextField"));
        Assert.assertNotNull(find("#passwordField"));
        Assert.assertNotNull(find("#testConnectionButton"));
        Assert.assertNotNull(find("#saveButton"));
        Assert.assertNotNull(find("#cancelButton"));
    }

    @Test
    public void shouldBlockElementsWithEmbedded() throws Exception {
        click("#dbTypeComboBox").click("Embedded DB");
        Assert.assertTrue(find("#serverTextField").isDisabled());
        Assert.assertTrue(find("#loginTextField").isDisabled());
        Assert.assertTrue(find("#passwordField").isDisabled());
    }

    @Test
    public void shouldUnblockElementsWithMysql() throws Exception {
        click("#dbTypeComboBox").click("MySQL");
        Assert.assertFalse(find("#serverTextField").isDisabled());
        Assert.assertFalse(find("#loginTextField").isDisabled());
        Assert.assertFalse(find("#passwordField").isDisabled());
    }

}
