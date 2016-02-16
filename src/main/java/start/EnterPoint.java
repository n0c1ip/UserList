package start;

import controllers.MainController;
import crudDB.EntityManagerFactory;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 */
public class EnterPoint extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainController mainController = new MainController();
        mainController.setPrimaryStage(primaryStage);
        mainController.setMainWindowTitle("User List");
        mainController.show();
    }

    @Override
    public void stop() throws Exception {
        EntityManagerFactory.closeEntityManagerFactory();
        super.stop();
    }

}

