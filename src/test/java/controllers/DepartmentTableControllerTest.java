package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import start.EnterPoint;

import java.io.IOException;

public class DepartmentTableControllerTest extends GuiTest {

    @Override
    protected Parent getRootNode() {

        MainController mainController = new MainController();
        mainController.setPrimaryStage(GuiTest.stage);
        mainController.initDialogController();
        FXMLLoader loader = new FXMLLoader(EnterPoint.class.getResource("/fxml/byDepartmentUserTable.fxml"));
        SplitPane table = new SplitPane();

        try {
            table = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DepartmentTableController controller = loader.getController();
        controller.setMainController(mainController);
        return table;
    }

    @Test
    public void shouldHaveOrganizationComboBox() throws Exception {
        Assert.assertNotNull(find("#organizationComboBox"));
    }

    @Test
    public void shouldHaveDepartmentList() throws Exception {
        Assert.assertNotNull(find("#departmentListView"));
    }

    @Test
    public void shouldHaveUserTableView() throws Exception {
        Assert.assertNotNull(find("#tableView"));
    }

    @Test
    public void shouldHaveUserManagementButtons() throws Exception {
        Assert.assertNotNull(find("#addButton"));
        Assert.assertNotNull(find("#changeButton"));
        Assert.assertNotNull(find("#removeButton"));
    }

    @Test
    public void shouldSortTable() throws Exception {
        click("#lastNameColumn");
        click("#firstNameColumn");
        click("#middleNameColumn");
        click("#departmentColumn");
        click("#positionColumn");
        click("#loginColumn");
        click("#passwordColumn");
        click("#mailColumn");
    }

    @Test
    public void shouldOpenUserEditDialog() throws Exception {
        click("#addButton");
        AnchorPane userEdit = find("#paneUserEdit");
        Assert.assertTrue(userEdit.getScene().getWindow().isShowing());
        type(KeyCode.ESCAPE);
    }
}
