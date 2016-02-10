package controllers;


import crud.LocationService;
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
import objects.Location;
import start.EnterPoint;

import java.io.IOException;

/**
 * Controller of Dialog for openning new tab with selected table
 */
public class ChooseTabController implements Dialog {

    private MainController mainController;
    private Stage dialog;
    private TabPane tabLayout;

    @FXML
    private ChoiceBox<Location> choiceBox;

    @FXML
    private void initialize(){
        ObservableList<Location> stringBox = FXCollections.observableArrayList();
        stringBox.addAll(LocationService.getAll());
        choiceBox.setItems(stringBox.sorted());
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
    public void loadNewTab(Location currentLocation){
        try {
            FXMLLoader loader = new FXMLLoader(EnterPoint.class.getResource("/fxml/userTable.fxml"));
            SplitPane table = loader.load();
            TableController controller = loader.getController();
            controller.setMainController(mainController);
            controller.setUserLocationTable(currentLocation);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(currentLocation.getName());
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
