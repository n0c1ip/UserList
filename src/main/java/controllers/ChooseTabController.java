package controllers;


import interfaces.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import start.EnterPoint;
import util.ListUtil;

import java.io.IOException;

/**
 * Controller of Dialog for openning new tab with selected table
 */
public class ChooseTabController implements Dialog {

    private MainController mainController;
    private Stage dialog;
    private TabPane tabLayout;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private void initialize(){
        ObservableList<String> stringBox = FXCollections.observableArrayList();
        stringBox.addAll(ListUtil.getMapStrings());
        choiceBox.setItems(stringBox);
        choiceBox.getSelectionModel().selectFirst();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void setDialog(Stage dialog) {
        this.dialog = dialog;
    }

    public void handleCancelButton() {
        this.dialog.close();
    }
    public void handleOpenButton(){
        loadNewTab(choiceBox.getSelectionModel().getSelectedItem());
        dialog.close();
    }
    public void loadNewTab(String currentTable){
        try {
            FXMLLoader loader = new FXMLLoader(EnterPoint.class.getResource("/fxml/userTable.fxml"));
            SplitPane table = loader.load();
            TableController controller = loader.getController();
            controller.setEnterPoint(mainController);
            controller.setCurrentTablename(currentTable);
            controller.setUserTable(currentTable);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(currentTable);
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
