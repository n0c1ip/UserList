package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import util.Fxml;
import util.I18n;

import java.io.IOException;

public class VirtualServerEditControllerTest extends GuiTest{

    @Override
    protected Parent getRootNode() {
        try {
            FXMLLoader rootLoader = Fxml.getFXMLLoader("vServerEditDialog.fxml");
            rootLoader.setResources(I18n.DIALOG.getResourceBundle());
            return rootLoader.<AnchorPane>load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldHaveAllElements() throws Exception {
        click("#serverNameField").type("1");
        click("#hostComboBox").push(KeyCode.ESCAPE);
        click("#serverIpAddressField").type("2");
        click("#serverRamField").type("3");
        click("#serverHddField").type("4");
        click("#serverOsField").type("5");
        click("#serverBackupTypeField").type("6");
        click("#backupTimeField").type("7");
        click("#backupServerField").type("8");
        click("#backupPathField").type("9");
        click("#scsiHostField").type("10");
        click("#scsiTargetField").type("11");
        click("#scsiHddField").type("12");
        click("#serverDescriptionField").type("13");


        Assert.assertTrue(find("#paneUserEdit").isVisible());
    }
}
