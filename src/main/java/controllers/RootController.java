package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import start.EnterPoint;

import java.io.IOException;

/**
 * Controller contains menu bar, handles menu bar items actions.
 */
public class RootController {


    public RootController() {
    }

    // References
    private MainController mainController;
    private TabPane tabLayout;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void showAddTabTableDialog() {

        try {
            final Stage dialog = new Stage();
            dialog.setTitle("Выбор объекта");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(mainController.getPrimaryStage());
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chooseLocation.fxml"));
            AnchorPane choosepane = loader.load();
            ChooseTabController controller = loader.getController();
            controller.setMainController(mainController);
            dialog.setScene(new Scene(choosepane));
            dialog.show();
        } catch (IOException e) {
            mainController.getDialogController().showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс выбора объекта");
            System.out.println(e.getStackTrace());
        }

    }

    public void showDepartmentTable(){
        try {
            FXMLLoader loader = new FXMLLoader(EnterPoint.class.getResource("/fxml/byDepartmentUserTable.fxml"));
            SplitPane table = loader.load();
            DepartmentTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab("Подразделения");
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            mainController.getDialogController().showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс подразделений");
            System.out.println(ex.getStackTrace());
        }
    }

    public void showImportFormCSV() {

        try {
            final Stage dialog = new Stage();
            dialog.setTitle("Импорт из CSV");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(mainController.getPrimaryStage());
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/importCSV.fxml"));
            AnchorPane importcsv = loader.load();
            ImportCSVController controller = loader.getController();
            controller.setMainController(mainController);
            dialog.setScene(new Scene(importcsv));
            dialog.show();
        } catch (IOException e) {
            mainController.getDialogController().showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс импорта из CSV");
            System.out.println(e.getStackTrace());
        }
    }

    @FXML
    private void handleAddDepartmentMenuItem() {
        mainController.getDialogController().showAddDepartmentDialog();
    }

    public void closeMainWindow(){
        mainController.getPrimaryStage().close();
    }


    public void handleCreateTableMenuItem() {

    }
}
