package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import objects.Department;
import objects.User;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import util.Fxml;
import util.I18n;

import java.io.IOException;

public class TableControllerTest extends GuiTest{

    @Override
    protected Parent getRootNode() {
        try {
            FXMLLoader rootLoader = Fxml.getFXMLLoader("byLocationUserTable.fxml");
            rootLoader.setResources(I18n.TABLE.getResourceBundle());
            SplitPane rootLayout = rootLoader.load();
            UsersInLocationTableController controller = rootLoader.getController();
            MainController mainController = new MainController();
            mainController.setPrimaryStage(GuiTest.stage);
            mainController.initDialogController();
            controller.setMainController(mainController);
            return rootLayout;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldSortColumn() throws Exception {
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
    public void shouldExistButtons(){
        Button addButton = find("#addButton");
        Assert.assertNotNull(addButton);
        Button changeButton = find("#changeButton");
        Assert.assertNotNull(changeButton);
        Button removeButton = find("#removeButton");
        Assert.assertNotNull(removeButton);
    }

    @Test
    public void shouldAddDataToTable(){
        TableView<User> tableView = find("#tableView");
        ObservableList<User> testList = FXCollections.observableArrayList();
        User testUser = new User();
        testUser.setDepartment(new Department("TestDepartment"));
        testUser.setLastName("LastName");
        testUser.setFirstName("FirstName");
        testList.add(testUser);
        tableView.setItems(testList);

        Assert.assertFalse(tableView.getItems().isEmpty());
    }



}
