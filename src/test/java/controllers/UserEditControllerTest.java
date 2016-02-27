package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import util.I18n;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;


public class UserEditControllerTest extends GuiTest {

    @Override
    protected Parent getRootNode() {
        try {
            FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/fxml/userEditDialog.fxml"));
            rootLoader.setResources(I18n.DIALOG.getResourceBundle());
            AnchorPane rootLayout = rootLoader.load();
            return rootLayout;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldHaveAllElements() throws Exception {
        assertNotNull(GuiTest.find("#paneUserEdit"));
        click("#lastNameField").type("a");
        click("#firstNameField").type("b");
        click("#middleNameField").type("c");
        click("#organizationComboBox").push(KeyCode.ESCAPE);
        click("#locationBox").push(KeyCode.ESCAPE);
        click("#departmentBox").push(KeyCode.ESCAPE);
        click("#positionField").type("d");
        click("#pcField").type("e");
        click("#loginField").type("f");
        click("#passwordField").type("g");
        click("#mailField").type("h");
        assertNotNull(GuiTest.find("#signUnlimitedButton"));
        assertNotNull(GuiTest.find("#okButton"));
        assertNotNull(GuiTest.find("#cancelButton"));
        //click("#cancelButton");
    }

}
