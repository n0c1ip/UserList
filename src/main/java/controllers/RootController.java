package controllers;

import crudFiles.SettingsService;
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
import objects.User;
import util.Fxml;
import util.I18n;
import util.Icons;

import java.io.File;
import java.io.IOException;

/**
 * Controller contains menu bar, handles menu bar items actions.
 */
public class RootController {

    @FXML
    private Menu menuTables;
    @FXML
    private Menu menuTablesStructure;
    @FXML
    private Menu itemEquipment;
    @FXML
    private MenuItem uploadInExcel;
    @FXML
    private MenuItem itemImportCsv;
    @FXML
    private Menu menuNetwork;

    public RootController() {
    }

    // References
    private MainController mainController;
    private TabPane tabLayout;

    @FXML
    private void initialize() {
        setMenuItems();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void showUsersByLocationTable() {
        try {
            FXMLLoader loader = Fxml.getFXMLLoader("byLocationUserTable.fxml");
            loader.setResources(I18n.TABLE.getResourceBundle());
            SplitPane table = loader.load();
            UsersInLocationTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(I18n.ROOT.getString("MenuBar.UsersByLocations"));
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            DialogController.showErrorDialog("Не удалось загрузить интерфейс пользователей по объектам");
        }

    }

    public void showPcTable(){
        try {
            FXMLLoader loader = Fxml.getFXMLLoader("pcTable.fxml");
            loader.setResources(I18n.TABLE.getResourceBundle());
            SplitPane table = loader.load();
            PcTableController controller = loader.getController();
            controller.setMainController(mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(I18n.ROOT.getString("MenuBar.Computers"));
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            DialogController.showErrorDialog("Не удалось загрузить таблицу компьютеров");
        }
    }

    public void showPhysicalServerTable(){
        try {
            FXMLLoader loader = Fxml.getFXMLLoader("pServerTable.fxml");
            loader.setResources(I18n.TABLE.getResourceBundle());
            SplitPane table = loader.load();
            PhysicalServerTableController controller = loader.getController();
            controller.setMainController(mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(I18n.ROOT.getString("MenuBar.Servers"));
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            DialogController.showErrorDialog("Не удалось загрузить таблицу серверов");
        }
    }

    public void showVirtualServerTable(){
        try {
            FXMLLoader loader = Fxml.getFXMLLoader("vServerTable.fxml");
            loader.setResources(I18n.TABLE.getResourceBundle());
            SplitPane table = loader.load();
            VirtualServerTableController controller = loader.getController();
            controller.setMainController(mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(I18n.ROOT.getString("MenuBar.VirtualServers"));
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            DialogController.showErrorDialog("Не удалось загрузить таблицу серверов");
        }
    }

    public void showOrganizationTable() {
         try {
            FXMLLoader loader = Fxml.getFXMLLoader("OrganizationTable.fxml");
            loader.setResources(I18n.TABLE.getResourceBundle());
            SplitPane table = loader.load();
            OrganizationTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(I18n.ROOT.getString("MenuBar.Organizations"));
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            DialogController.showErrorDialog("Не удалось загрузить интерфейс организаций");
            System.out.println(ex.getMessage());
        }
    }

    public void showLocationTable(){
        try {
            FXMLLoader loader = Fxml.getFXMLLoader("LocationTable.fxml");
            loader.setResources(I18n.TABLE.getResourceBundle());
            SplitPane table = loader.load();
            LocationTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(I18n.ROOT.getString("MenuBar.Locations"));
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            DialogController.showErrorDialog("Не удалось загрузить интерфейс объектов");
            System.out.println(ex.getMessage());
        }
    }

    public void showDepartmentsInOrganizationTable() {
        try {
            FXMLLoader loader = Fxml.getFXMLLoader("byOrganizationDepartmentsTable.fxml");
            loader.setResources(I18n.TABLE.getResourceBundle());
            SplitPane table = loader.load();
            DepartmentsInOrganizationTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(I18n.ROOT.getString("MenuBar.Departments"));
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            DialogController.showErrorDialog("Не удалось загрузить интерфейс подразделений по организации");
            System.out.println(ex.getMessage());
        }
    }

    public void showDepartmentTable(){
        try {
            FXMLLoader loader = Fxml.getFXMLLoader("byDepartmentUserTable.fxml");
            loader.setResources(I18n.TABLE.getResourceBundle());
            SplitPane table = loader.load();
            UsersInDepartmentTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(I18n.ROOT.getString("MenuBar.UsersByDepartments"));
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            DialogController.showErrorDialog("Не удалось загрузить интерфейс пользователей по подразделениям");
        }
    }

    public void showNetworkTable(){
        try {
            FXMLLoader loader = Fxml.getFXMLLoader("networkTable.fxml");
            loader.setResources(I18n.TABLE.getResourceBundle());
            SplitPane table = loader.load();
            NetworkTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(I18n.ROOT.getString("MenuBar.Networks"));
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            DialogController.showErrorDialog("Не удалось загрузить таблицу сетей");
        }
    }

    public void showVlanTable(){
        try {
            FXMLLoader loader = Fxml.getFXMLLoader("vlanTable.fxml");
            loader.setResources(I18n.TABLE.getResourceBundle());
            SplitPane table = loader.load();
            VlanTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab("VLANs");
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            DialogController.showErrorDialog("Не удалось загрузить таблицу VLAN");
        }
    }

    public void showSignUnlimitedTable() {
        try {
            FXMLLoader loader = Fxml.getFXMLLoader("SignUnlimitedTable.fxml");
            loader.setResources(I18n.TABLE.getResourceBundle());
            SplitPane table = loader.load();
            SignUnlimitedTableController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(I18n.ROOT.getString("MenuBar.SignUnlimited"));
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            DialogController.showErrorDialog("Не удалось загрузить интерфейс организаций");
            System.out.println(ex.getMessage());
        }
    }

    public void showClassificationTable(){
        try {
            FXMLLoader loader = Fxml.getFXMLLoader("classificationTable.fxml");
            loader.setResources(I18n.TABLE.getResourceBundle());
            SplitPane table = loader.load();
            ClassificationController controller = loader.getController();
            controller.setMainController(this.mainController);
            tabLayout = (TabPane) mainController.getRootLayout().getCenter();
            Tab tab = new Tab(I18n.ROOT.getString("MenuBar.Classification"));
            tab.setContent(table);
            tabLayout.getTabs().add(tab);
        } catch (IOException ex) {
            DialogController.showErrorDialog("Не удалось загрузить интерфейс классификатора");
            System.out.println(ex.getMessage());
        }
    }

    public void showImportFormCSV() {
        try {
            final Stage dialog = new Stage();
            dialog.getIcons().add(Icons.getImportIcon());
            dialog.setTitle(I18n.DIALOG.getString("Title.ImportFromCSV"));
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(mainController.getPrimaryStage());
            dialog.setResizable(false);
            FXMLLoader loader = Fxml.getFXMLLoader("importCSV.fxml");
            loader.setResources(I18n.DIALOG.getResourceBundle());
            AnchorPane importcsv = loader.load();
            ImportCSVController controller = loader.getController();
            controller.setMainController(mainController);
            dialog.setScene(new Scene(importcsv));
            dialog.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            DialogController.showErrorDialog("Не удалось загрузить интерфейс импорта из CSV");
        }
    }

    public void showPreferencesDialog() {
        try {
            final Stage dialog = new Stage();
            dialog.getIcons().add(Icons.getSettingsIcon());
            dialog.setTitle(I18n.DIALOG.getString("Title.Settings"));
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(mainController.getPrimaryStage());
            dialog.setResizable(false);
            FXMLLoader loader = Fxml.getFXMLLoader("settings.fxml");
            loader.setResources(I18n.DIALOG.getResourceBundle());
            Pane settingsPane = loader.load();
            SettingsController controller = loader.getController();
            controller.setTabLayout(tabLayout);
            dialog.setScene(new Scene(settingsPane));
            dialog.show();
        } catch (IOException e){
            DialogController.showErrorDialog("Не удалось загрузить интерфейс настроек");
            e.getStackTrace();
        }

    }

    public void closeMainWindow(){
        mainController.getPrimaryStage().close();
    }

    public void uploadInExcel() throws IOException {
        TabPane tabPane = (TabPane) mainController.getRootLayout().getCenter();
        if (tabPane.getSelectionModel().isEmpty()) {
            DialogController.showErrorDialog("Сначала откройте таблицу");
        } else {

            Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
            TableView currentTable = (TableView) currentTab.getContent().lookup("#tableView");
            ObservableList<TableColumn> columns = currentTable.getColumns();
            ObservableList<User> userList = currentTable.getItems();

            File file = mainController.getDialogController().showFileSaveDialog("Excel files (*.xls)","*.xls");

            if (file != null){
                UploadInExcelService.uploadInExcel(columns,userList,file);
                DialogController.showAlertDialog(Alert.AlertType.INFORMATION,
                        "Завершено", "Файл сохранен" + System.lineSeparator() + file.getAbsolutePath());
            }
        }
    }

    /**
     * Disables menu items if settings file is not present
     */
    public void setMenuItems(){
        boolean settingsIsMissing = !SettingsService.readSettings().isPresent();
        menuTables.setDisable(settingsIsMissing);
        menuTablesStructure.setDisable(settingsIsMissing);
        uploadInExcel.setDisable(settingsIsMissing);
        itemImportCsv.setDisable(settingsIsMissing);
        itemEquipment.setDisable(settingsIsMissing);
        menuNetwork.setDisable(settingsIsMissing);
    }

}
