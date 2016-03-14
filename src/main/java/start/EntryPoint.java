package start;

import controllers.MainController;
import crudDB.EntityManagerFactory;
import crudFiles.SettingsService;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import objects.Settings;
import util.ActiveUser;
import util.I18n;
import util.Icons;
import util.Permission;

import java.util.Locale;
import java.util.Optional;

public class EntryPoint extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Optional<Settings> optionalSettings = SettingsService.readSettings();
        if (optionalSettings.isPresent()) {
            Locale.setDefault(optionalSettings.get().getLanguageLocale());
            EntityManagerFactory.initialize();
        }

        MainController mainController = new MainController();
        primaryStage.getIcons().add(Icons.getApplicationIcon());
        mainController.setPrimaryStage(primaryStage);
        mainController.setMainWindowTitle(I18n.ROOT.getString("Program.Name"));
        mainController.show();

        if (optionalSettings.isPresent()) {
            if (SettingsService.isUserReadOnly(optionalSettings.get())) {
                ActiveUser.setPermissions(Permission.READ);
            } else {
                ActiveUser.setPermissions(Permission.READ, Permission.WRITE);
            }
        }
    }

    @Override
    public void stop() throws Exception {
        EntityManagerFactory.closeEntityManagerFactory();
        super.stop();
    }

}

