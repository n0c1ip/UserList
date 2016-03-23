package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import util.Fxml;
import util.I18n;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;

public class VlanEditControllerTest extends GuiTest{

    @Override
    protected Parent getRootNode() {
        FXMLLoader loader = Fxml.getFXMLLoader("vlanEditDialog.fxml");
        try {
            loader.setResources(I18n.DIALOG.getResourceBundle());
            return loader.<AnchorPane>load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldHaveAllElements() throws Exception {
        assertNotNull(GuiTest.find("#paneVlanEdit"));
        assertNotNull(GuiTest.find("#descriptionArea"));
        assertNotNull(GuiTest.find("#vlanNumberField"));
        assertNotNull(GuiTest.find("#networkComboBox"));
        assertNotNull(GuiTest.find("#okButton"));
        assertNotNull(GuiTest.find("#cancelButton"));
    }
}
