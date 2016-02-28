package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import org.junit.Ignore;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import util.Fxml;
import util.I18n;

import java.io.IOException;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;

public class RootControllerTest extends GuiTest {

    @Override
    protected Parent getRootNode() {

        try {
            FXMLLoader rootLoader = Fxml.getFXMLLoader("root.fxml");
            rootLoader.setResources(I18n.ROOT.getResourceBundle());
            BorderPane rootLayout = rootLoader.load();

            FXMLLoader tabpaneLoader = Fxml.getFXMLLoader("tabPane.fxml");
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

    @Test
    public void rootLayoutShouldHaveMenuBar() throws Exception {
        MenuBar menuBar = GuiTest.find("#menuBar");
        assertNotNull(menuBar);
        assertTrue(menuBar.isVisible());
    }

    @Test
    public void shouldHaveUsersByLocationTableItem() throws Exception {
        click("#menuTables");
        click("#itemUsersByLocationTable");
        assertNotNull(GuiTest.find("#paneLocationTable"));
        assertTrue(GuiTest.find("#paneLocationTable").isVisible());
    }

    @Test
    public void shouldHaveDeparmentTableItem() throws Exception {
        click("#menuTables");
        click("#itemDepartmentTable");
        assertNotNull(GuiTest.find("#paneDepartmentTable"));
        assertTrue(GuiTest.find("#paneDepartmentTable").isVisible());
    }

    @Test
    public void shouldHaveOrganizationTableItem() throws Exception {
        click("#menuTablesStructure");
        click("#itemOrganizationTable");
        assertNotNull(GuiTest.find("#organizationTableTotal"));
        assertTrue(GuiTest.find("#organizationTableTotal").isVisible());
    }

    @Test
    public void shouldHaveDepartmentInOrgTableItem() throws Exception {
        click("#menuTablesStructure");
        click("#itemDepartmentInOrgTable");
        assertNotNull(GuiTest.find("#deparmentsInOrganizationTable"));
        assertTrue(GuiTest.find("#deparmentsInOrganizationTable").isVisible());
    }

    @Test
    public void shouldHaveSignUnlimitedTableItem() throws Exception {
        click("#menuTables");
        click("#itemSignUnlimitedTable");
        assertNotNull(GuiTest.find("#signUnlimitedTableTotal"));
        assertTrue(GuiTest.find("#signUnlimitedTableTotal").isVisible());
    }

    @Test
    public void shouldHaveItemLocationTableItem() throws Exception {
        click("#menuTablesStructure");
        click("#itemLocationTable");
        assertNotNull(GuiTest.find("#itemLocationSplitPane"));
        assertTrue(GuiTest.find("#itemLocationSplitPane").isVisible());
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
