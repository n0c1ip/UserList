package controllers;


import interfaces.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import start.EnterPoint;
import util.ListUtil;

public class ChooseTabController implements Dialog {

    private EnterPoint enterPoint;
    private TabController tabController;
    private Stage dialog;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private void initialize(){
        ObservableList<String> stringBox = FXCollections.observableArrayList();
        stringBox.addAll(ListUtil.getMapStrings());
        choiceBox.setItems(stringBox);
        choiceBox.getSelectionModel().selectFirst();
    }



    public void setTabController(TabController tabController){
        this.tabController = tabController;
    }
    public void setEnterPoint(EnterPoint enterPoint) {
        this.enterPoint = enterPoint;
    }
    public void setDialog(Stage dialog) {
        this.dialog = dialog;
    }


    public void handleCancelButton() {

        this.dialog.close();
    }
    public void handleOpenButton(){
        enterPoint.loadNewTab(choiceBox.getSelectionModel().getSelectedItem());
        dialog.close();
    }
}
