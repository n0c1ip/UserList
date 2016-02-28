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

public class ClassificationControllerTest extends GuiTest{

    @Override
    protected Parent getRootNode() {
        MainController mainController = new MainController();
        mainController.setPrimaryStage(GuiTest.stage);
        mainController.initDialogController();
        FXMLLoader loader = new FXMLLoader(EntryPoint.class.getResource("/fxml/classificationTable.fxml"));
        loader.setResources(I18n.TABLE.getResourceBundle());
        SplitPane table = new SplitPane();

        try {
            table = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClassificationController controller = loader.getController();
        controller.setMainController(mainController);
        return table;
    }

    @Test
    public void shouldSortTable() throws Exception {
        click("#lastNameColumn");
        click("#firstNameColumn");
        click("#middleNameColumn");
        click("#departmentColumn");
        click("#positionColumn");
        click("#pcColumn");
        click("#loginColumn");
        click("#passwordColumn");
        click("#mailColumn");
    }

    @Test
    public void shouldHaveClassificationManagementButtons() throws Exception {
        Assert.assertNotNull(find("#addButton"));
        Assert.assertNotNull(find("#removeButton"));
    }

    @Test
    public void shouldHaveClassificationTable() throws Exception {
        Assert.assertNotNull(find("#classificationListView"));
    }

    @Test
    public void shouldHaveUserTable() throws Exception {
        Assert.assertNotNull(find("#tableView"));

    }
}
