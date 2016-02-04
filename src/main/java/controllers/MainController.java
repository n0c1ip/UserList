package controllers;// Created by mva on 04.02.2016.

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import objects.User;
import util.ListUtil;

import java.io.IOException;

/**
 * Main Controller initialize main window with Root Layout and
 * Tab Layout in it.
 */
public class MainController {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private DialogController dialogController;


    public void show() {
        addData();
        initRootLayout();
        initTabLayout();
        initDialogController();
    }

    //Some dummy data
    public void addData(){
        ListUtil.createUserList("Центральный офис");
        ListUtil.createUserList("Уволенные");
        ListUtil.createUserList("Логистика");
        ListUtil.createDepartment("Бухгалтерия");
        ListUtil.createDepartment("ИТ отдел");
        ListUtil.createDepartment("ЦТО");

        User user = new User("Vasiliy", "Pupkin", "","ИТ отдел","","","","");
    }

    /**
     * Initialization Root Layout, with Tab Layout in it.
     */
    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/root.fxml"));
            rootLayout = loader.load();
            RootController rootController = loader.getController();
            rootController.setMainController(this);
            primaryStage.setScene(new Scene(rootLayout));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialization Tab Layout
     * @return Tab Controller in RootLayout
     */
    private void initTabLayout() {
        TabController tabController = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tabpane.fxml"));
            TabPane tabPane = loader.load();
            tabController = loader.getController();
            rootLayout.setCenter(tabPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDialogController(){
        DialogController dialogController = new DialogController();
        dialogController.setPrimaryStage(primaryStage);
        this.dialogController = dialogController;

    }

    public void setMainWindowTitle(String title){
        this.primaryStage.setTitle(title);
    }
    public BorderPane getRootLayout() {
        return rootLayout;
    }
    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public DialogController getDialogController() {
        return dialogController;
    }
}
