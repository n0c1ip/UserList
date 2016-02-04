package start;

import controllers.RootController;
import controllers.TabController;
import controllers.UserEditController;
import javafx.application.Application;
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

public class EnterPoint extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    public static void main(String[] args) {
        launch(args);
    }

    public EnterPoint(){

        ListUtil.createUserList("Центральный офис");
        ListUtil.createUserList("Уволенные");
        ListUtil.createUserList("Логистика");
        ListUtil.createDepartment("Бухгалтерия");
        ListUtil.createDepartment("ИТ отдел");
        ListUtil.createDepartment("ЦТО");

        User user = new User("Vasiliy", "Pupkin", "","ИТ отдел","","","","");

    }

    //starts here
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("User List");
//        this.primaryStage.getIcons().add(new Image("file:res/icon.png"));

        initRootLayout();

    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/root.fxml"));
            rootLayout = loader.load();
            RootController rootController = loader.getController();
            rootController.setEnterPoint(this);
            primaryStage.setScene(new Scene(rootLayout));
            primaryStage.show();
            rootController.setTabController(initTabLayout());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TabController initTabLayout() {
        TabController tabController = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tabpane.fxml"));
            TabPane tabPane = (TabPane) loader.load();
            tabController = loader.getController();
            tabController.setEnterPoint(this);
            rootLayout.setCenter(tabPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tabController;
    }

    public BorderPane getRootLayout(){
        return this.rootLayout;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void showUserEditDialog(String currentTable, String title, User user) {
        try{
            final Stage dialog = new Stage();
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(getPrimaryStage());
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userEdit.fxml"));
            AnchorPane useredit = (AnchorPane) loader.load();
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


}

