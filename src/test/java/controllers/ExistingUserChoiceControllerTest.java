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

public class ExistingUserChoiceControllerTest extends GuiTest {

    @Override
    protected Parent getRootNode() {

        MainController mainController = new MainController();
        mainController.setPrimaryStage(GuiTest.stage);
        mainController.initDialogController();
        FXMLLoader loader = new FXMLLoader(EntryPoint.class.getResource("/fxml/existingUserChoiceTable.fxml"));
        loader.setResources(I18n.TABLE.getResourceBundle());
        SplitPane table = new SplitPane();

        try {
            table = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExistingUserChoiceController controller = loader.getController();
        return table;
    }


    @Test
    public void shouldHaveUserTableView() throws Exception {
        Assert.assertNotNull(find("#tableView"));
    }

    @Test
    public void shouldHaveUserChoiceButtons() throws Exception {
        Assert.assertNotNull(find("#choiceButton"));
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

}
