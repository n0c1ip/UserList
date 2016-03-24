package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import util.Fxml;
import util.I18n;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class PcTableControllerTest extends GuiTest {

    @Override
    protected Parent getRootNode() {

        MainController mainController = new MainController();
        mainController.setPrimaryStage(GuiTest.stage);
        mainController.initDialogController();
        FXMLLoader loader = Fxml.getFXMLLoader("pcTable.fxml");
        loader.setResources(I18n.TABLE.getResourceBundle());
        SplitPane table = new SplitPane();
        try {
            table = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PcTableController controller = loader.getController();
        controller.setMainController(mainController);
        return table;
    }

    @Test
    public void shouldSortColumn() throws Exception {
        click("#pcNameColumn");
        click("#pcIpAddressType");
        click("#pcIpAddress");
        click("#pcVlan");
        click("#pcUserName");
    }

    @Test
    public void shouldHaveManagementButtons() throws Exception {
        assertNotNull(find("#addButton"));
        assertNotNull(find("#changeButton"));
        assertNotNull(find("#removeButton"));
    }

    @Test
    public void shouldHaveTableView() throws Exception {
        assertNotNull(find("#tableView"));
    }

    @Test
    public void shouldOpenPcEditDialog() throws Exception {
        find("#addButton").setDisable(false);
        click("#addButton");
        AnchorPane userEdit = find("#panePcEdit");
        assertTrue(userEdit.getScene().getWindow().isShowing());
        type(KeyCode.ESCAPE);
    }


}
