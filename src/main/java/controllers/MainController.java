package controllers;

import crudDB.EntityManagerFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.Fxml;
import util.I18n;

import java.io.IOException;

/**
 * Main Controller initialize main window with Root Layout and
 * Tab Layout in it.
 */
public class MainController{

    private DialogController dialogController;
    private Stage primaryStage;
    private BorderPane rootLayout;

    public void show() {
        EntityManagerFactory.initialize();
        initDialogController();
        initRootLayout();
        initTabLayout();
    }

    public void initDialogController(){
        DialogController dialogController = new DialogController();
        dialogController.setPrimaryStage(primaryStage);
        dialogController.setMainController(this);
        this.dialogController = dialogController;
    }

    /**
     * Initialization Root Layout, with Tab Layout in it.
     */
    private void initRootLayout() {
        try {
            FXMLLoader loader = Fxml.getFXMLLoader("root.fxml");
            loader.setResources(I18n.ROOT.getResourceBundle());
            rootLayout = loader.load();
            RootController rootController = loader.getController();
            rootController.setMainController(this);
            primaryStage.setScene(new Scene(rootLayout));
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            DialogController.showAlertDialog(Alert.AlertType.ERROR, I18n.ERROR.getString("Error"), I18n.ERROR.getString("ErrorRootLayoutLoader"));
        }
    }

    /**
     * Initialization Tab Layout
     */
    private void initTabLayout() {
        try {
            FXMLLoader loader = Fxml.getFXMLLoader("tabPane.fxml");
            TabPane tabPane = loader.load();
            rootLayout.setCenter(tabPane);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            DialogController.showAlertDialog(Alert.AlertType.ERROR, I18n.ERROR.getString("Error"), I18n.ERROR.getString("ErrorTabPaneLoader"));
        }
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
