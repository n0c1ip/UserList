package controllers;

import crudDB.LocationService;
import crudDB.OrganizationService;
import crudFiles.UploadInExcelService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Location;
import objects.Organization;
import objects.User;
import start.EntryPoint;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

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

    public void showUsersByLocationTable() {

        try {
            final Stage dialog = new Stage();
            dialog.setTitle("Выбор объекта");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(mainController.getPrimaryStage());
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chooseLocation.fxml"));
            AnchorPane anchorPane = loader.load();
            ChooseTabController controller = loader.getController();
            controller.setMainController(mainController);
            dialog.setScene(new Scene(anchorPane));
            dialog.show();
        } catch (IOException e) {
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс выбора объекта");
            System.out.println(e.getStackTrace());
        }

    }

    public void showOrganizationTable() {
         try {
           FXMLLoader loader = new FXMLLoader(EnterPoint.class.getResource("/fxml/OrganizationTable.fxml"));
            SplitPane table = loader.load();
            OrganizationTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab("Организации");
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс организаций");
            System.out.println(ex.getMessage());
        }
    }

    public void showDepartmentsInOrganizationTable() {

        try {
            FXMLLoader loader = new FXMLLoader(EntryPoint.class.getResource("/fxml/byOrganizationDepartmentsTable.fxml"));
            SplitPane table = loader.load();
            DepartmentsInOrganizationTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab("Подразделения");
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс подразделений по организации");
            System.out.println(ex.getMessage());
        }
    }

    public void showDepartmentTable(){
        try {
            FXMLLoader loader = new FXMLLoader(EntryPoint.class.getResource("/fxml/byDepartmentUserTable.fxml"));
            SplitPane table = loader.load();
            DepartmentTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab("Пользователи по подразделению");
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс пользователей по подразделениям");
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
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс импорта из CSV");
            System.out.println(e.getStackTrace());
        }
    }

    public void showPreferencesDialog() {
        try {
            final Stage dialog = new Stage();
            dialog.setTitle("Настройки");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(mainController.getPrimaryStage());
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/settings.fxml"));
            Pane settingsPane = loader.load();
            SettingsController controller = loader.getController();
            controller.setMainController(mainController);
            dialog.setScene(new Scene(settingsPane));
            dialog.show();
        } catch (IOException e){
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс настроек");
            e.getStackTrace();
        }

    }


    @FXML
    public void handleAddLocationMenuItem(){
        Optional<String> nameOptional = mainController.getDialogController().showAddLocationDialog();
        if (nameOptional.isPresent() && !nameOptional.get().isEmpty()) {
            Location location = new Location(nameOptional.get());
            Location addedLocation = LocationService.add(location);
        }
    }

    @FXML
    public void handleAddOrganizationMenuItem(){
        Optional<String> nameOptional = mainController.getDialogController().showAddOrganizationDialog();
        if (nameOptional.isPresent() && !nameOptional.get().isEmpty()) {
            Organization organization = new Organization(nameOptional.get());
            Organization addedOrganization = OrganizationService.add(organization);
        }
    }


    public void closeMainWindow(){
        mainController.getPrimaryStage().close();
    }

    public void uploadInExcel() throws IOException {

        TabPane tabPane = (TabPane) mainController.getRootLayout().getCenter();
        if (tabPane.getSelectionModel().isEmpty()) {
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Сначала откройте таблицу");
        } else {

            Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
            TableView currentTable = (TableView) currentTab.getContent().lookup("#tableView");
            ObservableList<TableColumn> columns = currentTable.getColumns();
            ObservableList<User> userList = currentTable.getItems();

            File file = mainController.getDialogController().showFileSaveDialog("Excel files (*.xls)","*.xls");

            if (file != null){
                UploadInExcelService.uploadInExcel(columns,userList,file);
            }
        }
    }
}
