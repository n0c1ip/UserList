package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import util.Fxml;
import util.I18n;

import java.io.IOException;

public class LocationEditControllerTest extends GuiTest{

    @Override
    protected Parent getRootNode() {
        try {
            FXMLLoader rootLoader = Fxml.getFXMLLoader("LocationEditDialog.fxml");
            rootLoader.setResources(I18n.DIALOG.getResourceBundle());
            return rootLoader.<AnchorPane>load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldHaveAllElements() throws Exception {
        click("#locationNameField").type("1");
        click("#locationCityField").type("2");
        click("#locationAddressField").type("3");
        Assert.assertTrue(find("#paneLocationEdit").isVisible());
    }
}
