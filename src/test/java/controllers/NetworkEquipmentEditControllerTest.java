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

public class NetworkEquipmentEditControllerTest extends GuiTest{

    @Override
    protected Parent getRootNode() {
        try {
            FXMLLoader rootLoader = Fxml.getFXMLLoader("networkEquipmentEditDialog.fxml");
            rootLoader.setResources(I18n.DIALOG.getResourceBundle());
            return rootLoader.<AnchorPane>load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldHaveAllElements() throws Exception {
        click("#neNameField").type("1");
        click("#neModelField").type("2");
        click("#neIpAddressField").type("3");
        click("#neTypeField").type("4");
        click("#neLocationField").type("5");
        click("#neDescriptionField").type("6");
        Assert.assertTrue(find("#paneUserEdit").isVisible());
    }
}
