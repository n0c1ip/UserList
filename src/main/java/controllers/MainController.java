package controllers;// Created by mva on 04.02.2016.

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.User;
import util.ListUtil;

import java.io.IOException;

/**
 * Main Controller initialize main window with Root Layout and
 * Tab Layout in it. Contains menu bar, handles menu bar items actions.
 */
public class MainController {

    private Stage primaryStage;
    private BorderPane rootLayout;


    public void show() {
        addData();
        initRootLayout();
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
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/root.fxml"));
            rootLayout = loader.load();
            RootController rootController = loader.getController();
            rootController.setMainController(this);
            primaryStage.setScene(new Scene(rootLayout));
            primaryStage.show();
            rootController.setTabController(initTabLayout());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialization Tab Layout
     * @return Tab Controller in RootLayout
     */
    public TabController initTabLayout() {
        TabController tabController = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tabpane.fxml"));
            TabPane tabPane = loader.load();
            tabController = loader.getController();
            rootLayout.setCenter(tabPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tabController;
    }

    /**
     * Dialog shows when called adding or editing User
     * @param currentTable table which from called method(now dispalyed)
     * @param title Dialog title, depends (add\edit)
     * @param user editing User
     */
    public void showUserEditDialog(String currentTable, String title, User user) {
        try{
            final Stage dialog = new Stage();
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(getPrimaryStage());
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userEdit.fxml"));
            AnchorPane useredit = loader.load();
            UserEditController controller = loader.getController();
            controller.setDialog(dialog);
            controller.setEditedUser(user);
            controller.setCurrentTable(currentTable);
            dialog.setScene(new Scene(useredit));
            dialog.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
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

}
