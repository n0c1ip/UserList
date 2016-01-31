package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import start.EnterPoint;

public class TabController {

    @FXML
    private TabPane tabPane;

    @FXML
    public void initialize(){


    }

        private EnterPoint enterPoint;

    public void setMainApp(EnterPoint enterPoint) {
        this.enterPoint = enterPoint;
    }






}
