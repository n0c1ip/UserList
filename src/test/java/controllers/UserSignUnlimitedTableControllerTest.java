package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import util.Fxml;
import util.I18n;

import java.io.IOException;

public class UserSignUnlimitedTableControllerTest extends GuiTest{

    @Override
    protected Parent getRootNode() {

        try {
            FXMLLoader rootLoader = Fxml.getFXMLLoader("userSignUnlimitedTable.fxml");
            rootLoader.setResources(I18n.TABLE.getResourceBundle());
            rootLoader.setResources(I18n.TABLE.getResourceBundle());
            return rootLoader.<SplitPane>load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldHaveManagementButtons() throws Exception {
        Assert.assertNotNull(find("#addButton"));
        Assert.assertNotNull(find("#changeButton"));
        Assert.assertNotNull(find("#removeButton"));
    }

    @Test
    public void shouldBeVisible() throws Exception {
        Assert.assertNotNull(find("#signUnlimitedTableTotal").isVisible());
    }

    @Test
    public void shouldSortColumns() throws Exception {
        click("#signUnlimitedNameColumn");
        click("#userSignUnlimitedValueColumn");
    }

}
