package controllers;
//Created by mva on 29.01.2016.

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import start.EnterPoint;

import java.io.IOException;

public class RootController {

    @FXML
    private MenuItem tableViewItem;
    @FXML
    private MenuItem tableItem;


    public RootController() {
    }

    // References
    private EnterPoint enterPoint;
    private TabController tabcontroller;

    public void setMainApp(EnterPoint enterPoint) {
        this.enterPoint = enterPoint;
    }

    public void setTabController(TabController tabcontroller){
        this.tabcontroller = tabcontroller;
    }

    public void setTable(ActionEvent actionEvent) {
        if(actionEvent.getSource() == tableItem){

        }
        if (actionEvent.getSource() == tableViewItem){

        }

    }

    public void addTabTable(ActionEvent actionEvent) {

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

}
