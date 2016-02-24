package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import start.EntryPoint;
import util.I18n;

import java.io.IOException;

public class SignUnlimitedTableControllerTest extends GuiTest {

    @Override
    protected Parent getRootNode() {

        MainController mainController = new MainController();
        mainController.setPrimaryStage(GuiTest.stage);
        mainController.initDialogController();
        FXMLLoader loader = new FXMLLoader(EntryPoint.class.getResource("/fxml/SignUnlimitedTable.fxml"));
        loader.setResources(I18n.TABLE.getBundle());
        SplitPane table = new SplitPane();

        try {
            table = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SignUnlimitedTableController controller = loader.getController();
        controller.setMainController(mainController);
        return table;
    }

    @Test
    public void shouldHaveOrganizationTableView() throws Exception {
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
        click("#signUnlimitedNameColumn");
    }

    @Test
    public void shouldOpenUserEditDialog() throws Exception {
        click("#addButton");
        AnchorPane userEdit = find("#paneSignUnlimitedEdit");
        Assert.assertTrue(userEdit.getScene().getWindow().isShowing());
        type(KeyCode.ESCAPE);
    }
}
