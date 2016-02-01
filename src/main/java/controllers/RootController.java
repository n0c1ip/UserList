package controllers;
//Created by mva on 29.01.2016.

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import start.EnterPoint;

import java.io.File;
import java.io.IOException;

public class RootController {


    public RootController() {
    }

    // References
    private EnterPoint enterPoint;
    private TabController tabcontroller;
    private TabPane tabLayout;
    public void setMainApp(EnterPoint enterPoint) {
        this.enterPoint = enterPoint;
    }
    public void setTabController(TabController tabcontroller){
        this.tabcontroller = tabcontroller;
    }



    public void showAddTabTableDialg(ActionEvent actionEvent) {

        try {
            final Stage dialog = new Stage();
            dialog.setTitle("Выбор таблицы");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(enterPoint.getPrimaryStage());
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chooseTable.fxml"));
            AnchorPane choosepane = (AnchorPane) loader.load();
            ChooseTabController controller = loader.getController();
            controller.setTabController(tabcontroller);
            controller.setEnterPoint(enterPoint);
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
            SplitPane table = (SplitPane) loader.load();
            DepartmentTableController controller = loader.getController();
            tabLayout = (TabPane) enterPoint.getRootLayout().getCenter();
            Tab tab = new Tab("Departments");
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showImportFormCSV(ActionEvent actionEvent) {

        try {
            final Stage dialog = new Stage();
            dialog.setTitle("Импорт из CSV");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(enterPoint.getPrimaryStage());
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/importCSV.fxml"));
            AnchorPane importcsv = (AnchorPane) loader.load();
            ImportCSVController controller = loader.getController();
            controller.setEnterPoint(enterPoint);
            controller.setDialog(dialog);
            dialog.setScene(new Scene(importcsv));
            dialog.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
