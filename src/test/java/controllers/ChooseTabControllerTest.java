package controllers;

import crud.LocationService;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import objects.Location;
import objects.User;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;


public class ChooseTabControllerTest extends GuiTest {

    private TabPane tabPane;
    private String locationName;

    @Override
    protected Parent getRootNode() {
        MainController mainController = new MainController();
        mainController.setPrimaryStage(GuiTest.stage);
        mainController.show();

        ChooseTabController chooseTabController = new ChooseTabController();
        chooseTabController.setMainController(mainController);
        Location location = LocationService.getAll().get(0);
        locationName = location.getName();
        tabPane = chooseTabController.loadNewTab(location);

        return new BorderPane();
    }

    @Test
    public void shouldLoadNewTab() {
        Assert.assertNotNull(tabPane);
        Assert.assertNotNull(tabPane.getTabs().get(0).getContent());
        Assert.assertEquals(locationName, tabPane.getTabs().get(0).getText());
        Assert.assertEquals(1, tabPane.getTabs().size());
    }

}