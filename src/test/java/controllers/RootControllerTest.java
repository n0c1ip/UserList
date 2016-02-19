package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import org.junit.Ignore;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;
import static org.loadui.testfx.Assertions.assertNodeExists;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;

public class RootControllerTest extends GuiTest {

    @Override
    protected Parent getRootNode() {

        try {
            FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/fxml/root.fxml"));
            BorderPane rootLayout = rootLoader.load();

            FXMLLoader tabpaneLoader = new FXMLLoader(getClass().getResource("/fxml/tabpane.fxml"));
            TabPane tabPane = tabpaneLoader.load();
            rootLayout.setCenter(tabPane);

            MainController mainController = new MainController();
            mainController.setPrimaryStage(stage);
            mainController.setRootLayout(rootLayout);
            mainController.initDialogController();

            RootController rootController = rootLoader.getController();
            rootController.setMainController(mainController);

            return rootLayout;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Ignore
    public void shouldHaveCloseWindowItem() throws Exception {
        click("#menuFile");
        click("#itemCloseWindow");
        assertNull(GuiTest.find("#menuBar"));
    }

    @Test
    public void rootLayoutShouldHaveMenuBar() throws Exception {
        MenuBar menuBar = GuiTest.find("#menuBar");
        assertNotNull(menuBar);
        assertTrue(menuBar.isVisible());
    }

    @Test
    public void shouldHaveAddTableItem() throws Exception {
        click("#menuTables");
        click("#itemAddTabTable");
        assertNotNull(GuiTest.find("#choiceBox"));
        assertTrue(GuiTest.find("#choiceBox").isVisible());
        assertTrue(GuiTest.find("#choiceBox").isFocused());
        verifyThat("#chiceOpen", hasText("Открыть"));
        click("#choiceCancel");
    }

    @Test
    public void shouldHaveShowDeparmentTableItem() throws Exception {
        click("#menuTables");
        click("#itemDepartmentTable");
        assertNotNull(GuiTest.find("#paneDepartmentTable"));
        assertTrue(GuiTest.find("#paneDepartmentTable").isVisible());
    }

    @Test
    public void shouldHaveShowDeparmentsInOrganizationTableItem() throws Exception {
        click("#menuTables");
        click("#itemDeparmentInOrgTable");
        assertNotNull(GuiTest.find("#deparmentsInOrganizationTable"));
        assertTrue(GuiTest.find("#deparmentsInOrganizationTable").isVisible());
    }

    @Test
    public void shouldHaveImportCsvItem() throws Exception {
        click("#menuService");
        click("#itemImportCsv");
        assertNotNull(GuiTest.find("#paneImportCsv"));
        assertTrue(GuiTest.find("#paneImportCsv").isVisible());
        click("#choiceCancel");
    }

    @Test
    public void shouldHavePreferencesItem() throws Exception {
        click("#menuService");
        click("#preferences");
        click("#cancelButton");
    }
}
