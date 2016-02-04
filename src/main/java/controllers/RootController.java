package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import start.EnterPoint;

import java.io.IOException;

public class RootController {


    public RootController() {
    }

    // References
    private MainController mainController;
    private TabController tabcontroller;
    private TabPane tabLayout;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setTabController(TabController tabcontroller){
        this.tabcontroller = tabcontroller;
    }

    public void showAddTabTableDialog() {

        try {
            final Stage dialog = new Stage();
            dialog.setTitle("Выбор таблицы");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(mainController.getPrimaryStage());
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chooseTable.fxml"));
            AnchorPane choosepane = loader.load();
            ChooseTabController controller = loader.getController();
            controller.setTabController(tabcontroller);
            controller.setMainController(mainController);
            controller.setDialog(dialog);
            dialog.setScene(new Scene(choosepane));
            dialog.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void showDepartmentTable(){
        try {
            FXMLLoader loader = new FXMLLoader(EnterPoint.class.getResource("/fxml/departmentTable.fxml"));
            SplitPane table = loader.load();
            DepartmentTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab("Подразделения");
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showImportFormCSV() {

        try {
            final Stage dialog = new Stage();
            dialog.setTitle("Импорт из CSV");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(mainController.getPrimaryStage());
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/importCSV.fxml"));
            AnchorPane importcsv = loader.load();
            ImportCSVController controller = loader.getController();
            controller.setMainController(mainController);
            controller.setDialog(dialog);
            dialog.setScene(new Scene(importcsv));
            dialog.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
