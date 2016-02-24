package controllers;

import crudFiles.UploadInExcelService;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.User;
import start.EntryPoint;
import util.I18n;

import java.io.File;
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

    public void showUsersByLocationTable() {

        try {
            FXMLLoader loader = new FXMLLoader(EntryPoint.class.getResource("/fxml/byLocationUserTable.fxml"));
            loader.setResources(I18n.TABLE.getBundle());
            SplitPane table = loader.load();
            UsersInLocationTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(I18n.ROOT.getString("MenuBar.UsersByLocations"));
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс пользователей по объектам");
            System.out.println(ex.getStackTrace());
        }

    }

    public void showOrganizationTable() {
         try {
            FXMLLoader loader = new FXMLLoader(EntryPoint.class.getResource("/fxml/OrganizationTable.fxml"));
            loader.setResources(I18n.TABLE.getBundle());
            SplitPane table = loader.load();
            OrganizationTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(I18n.ROOT.getString("MenuBar.Organizations"));
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс организаций");
            System.out.println(ex.getMessage());
        }
    }

    public void showLocationTable(){
        try {
            FXMLLoader loader = new FXMLLoader(EntryPoint.class.getResource("/fxml/LocationTable.fxml"));
            loader.setResources(I18n.TABLE.getBundle());
            SplitPane table = loader.load();
            LocationTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(I18n.ROOT.getString("MenuBar.Locations"));
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс объектов");
            System.out.println(ex.getMessage());
        }
    }

    public void showDepartmentsInOrganizationTable() {

        try {
            FXMLLoader loader = new FXMLLoader(EntryPoint.class.getResource("/fxml/byOrganizationDepartmentsTable.fxml"));
            loader.setResources(I18n.TABLE.getBundle());
            SplitPane table = loader.load();
            DepartmentsInOrganizationTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(I18n.ROOT.getString("MenuBar.Departments"));
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
            loader.setResources(I18n.TABLE.getBundle());
            SplitPane table = loader.load();
            UsersInDepartmentTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(I18n.ROOT.getString("MenuBar.UsersByDepartments"));
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            DialogController.showAlertDialog(Alert.AlertType.ERROR, I18n.ERROR.getString("Error"), "Не удалось загрузить интерфейс пользователей по подразделениям");
            System.out.println(ex.getStackTrace());
        }
    }

    public void showImportFormCSV() {

        try {
            final Stage dialog = new Stage();
            dialog.getIcons().add(new Image("icons/import-icon.png"));
            dialog.setTitle(I18n.DIALOG.getString("Title.ImportFromCSV"));
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(mainController.getPrimaryStage());
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/importCSV.fxml"));
            loader.setResources(I18n.DIALOG.getBundle());
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
            dialog.getIcons().add(new Image("icons/settings-icon.png"));
            dialog.setTitle(I18n.DIALOG.getString("Title.Settings"));
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(mainController.getPrimaryStage());
            dialog.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/settings.fxml"));
            loader.setResources(I18n.DIALOG.getBundle());
            Pane settingsPane = loader.load();
            SettingsController controller = loader.getController();
            controller.setMainController(mainController);
            controller.setTabLayout(tabLayout);
            dialog.setScene(new Scene(settingsPane));
            dialog.show();
        } catch (IOException e){
            DialogController.showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс настроек");
            e.getStackTrace();
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
                DialogController.showAlertDialog(Alert.AlertType.INFORMATION,
                        "Завершено", "Файл сохранен" + "\n" + file.getAbsolutePath());
            }
        }
    }
}
