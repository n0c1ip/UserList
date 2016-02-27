package start;

import controllers.MainController;
import crudDB.EntityManagerFactory;
import crudFiles.SettingsService;
import javafx.application.Application;
import javafx.stage.Stage;
import objects.Settings;
import util.I18n;
import util.Icons;
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
        }

        MainController mainController = new MainController();
        primaryStage.getIcons().add(Icons.getApplicationIcon());
        mainController.setPrimaryStage(primaryStage);
        mainController.setMainWindowTitle(I18n.ROOT.getString("Program.Name"));
        mainController.show();
    }

    @Override
    public void stop() throws Exception {
        EntityManagerFactory.closeEntityManagerFactory();
        super.stop();
    }

}

