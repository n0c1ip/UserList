package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import start.EnterPoint;

import java.io.File;
import java.io.FileOutputStream;
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
            mainController.getDialogController().showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить интерфейс настроек");
            e.getStackTrace();
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

    public void uploadInExcel() throws IOException {

        //Tab with table
        Tab currentTab = tabLayout.getSelectionModel().getSelectedItem();
        //Current viewed table
        TableView currentTable = (TableView) currentTab.getContent().lookup("#tableView");
        //Columns in table
        ObservableList<TableColumn> columns = currentTable.getColumns();
        //Users in table
        ObservableList<User> userList= currentTable.getItems();
        //Column count
        final int columnCount = columns.size();

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(currentTab.getText());

        //creating titles
        Row row = sheet.createRow(0);
        for (int i = 0; i < columnCount; i++) {
            row.createCell(i).setCellValue(columns.get(i).getText());
        }

        //fill table with users data
        for (int i = 0; i < userList.size(); i++) {
            Row userRow = sheet.createRow(i+1);
            userRow.createCell(0).setCellValue(userList.get(i).getLastName());
            userRow.createCell(1).setCellValue(userList.get(i).getFirstName());
            userRow.createCell(2).setCellValue(userList.get(i).getMiddleName());
            userRow.createCell(3).setCellValue(userList.get(i).getDepartment().toString());
            userRow.createCell(4).setCellValue(userList.get(i).getPosition());
            userRow.createCell(5).setCellValue(userList.get(i).getLogin());
            userRow.createCell(6).setCellValue(userList.get(i).getPassword());
            userRow.createCell(7).setCellValue(userList.get(i).getMail());
        }

        //autoSizeColumn should be call after data added to table
        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);
        }

        //Save file dialog
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(mainController.getPrimaryStage());

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        workbook.write(fileOutputStream);

        fileOutputStream.close();
        workbook.close();
    }
}
