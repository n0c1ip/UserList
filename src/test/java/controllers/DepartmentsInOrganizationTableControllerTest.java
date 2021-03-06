package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import util.Fxml;
import util.I18n;

import java.io.IOException;

public class DepartmentsInOrganizationTableControllerTest extends GuiTest {

    @Override
    protected Parent getRootNode() {

        MainController mainController = new MainController();
        mainController.setPrimaryStage(GuiTest.stage);
        mainController.initDialogController();
        FXMLLoader loader = Fxml.getFXMLLoader("byOrganizationDepartmentsTable.fxml");
        loader.setResources(I18n.TABLE.getResourceBundle());
        SplitPane table = new SplitPane();

        try {
            table = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DepartmentsInOrganizationTableController controller = loader.getController();
        controller.setMainController(mainController);
        return table;
    }

    @Test
    public void shouldHaveAllElements() throws Exception {
        Assert.assertNotNull(find("#organizationListView"));
        Assert.assertNotNull(find("#tableView"));
        Assert.assertNotNull(find("#departmentNameColumn"));

        Assert.assertNotNull(find("#addButton"));
        Assert.assertNotNull(find("#changeButton"));
        Assert.assertNotNull(find("#removeButton"));
    }

    @Test
    public void shouldSortTable() throws Exception {
        click("#departmentNameColumn");
    }

    @Test
    public void shouldOpenDepartmentEditDialog() throws Exception {
        find("#addButton").setDisable(false);
        click("#addButton");
        AnchorPane departmentEdit = find("#paneDepartmentEdit");
        Assert.assertTrue(departmentEdit .getScene().getWindow().isShowing());
        type(KeyCode.ESCAPE);
    }
}
