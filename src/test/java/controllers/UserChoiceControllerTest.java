package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import start.EntryPoint;
import util.I18n;

import java.io.IOException;

public class UserChoiceControllerTest extends GuiTest{

    @Override
    protected Parent getRootNode() {
        MainController mainController = new MainController();
        mainController.setPrimaryStage(GuiTest.stage);
        mainController.initDialogController();
        FXMLLoader loader = new FXMLLoader(EntryPoint.class.getResource("/fxml/userChoiceDialog.fxml"));
        loader.setResources(I18n.TABLE.getResourceBundle());
        SplitPane table = new SplitPane();

        try {
            table = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    @Test
    public void shouldHaveDepartmentListView() throws Exception {
        Assert.assertNotNull(find("#departmentListView"));
    }

    @Test
    public void shouldHaveUsersTable() throws Exception {
        Assert.assertNotNull(find("#tableView"));
    }

    @Test
    public void shouldSortTable() throws Exception {
        click("#lastNameColumn");
        click("#firstNameColumn");
        click("#middleNameColumn");
        click("#loginColumn");
    }

    @Test
    public void shouldHaveOrganizationComboBox() throws Exception {
        click("#organizationComboBox");
        click("#organizationComboBox");
    }

    @Test
    public void shouldFilterUsers() throws Exception {
        click("#searchField").type("a");
    }

    @Test
    public void shouldHaveChoiceButton() throws Exception {
        Assert.assertNotNull(find("#choiceButton"));
    }
}
