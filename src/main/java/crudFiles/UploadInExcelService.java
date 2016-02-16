package crudFiles;


import controllers.MainController;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import objects.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UploadInExcelService {

    public static void uploadInExcel(MainController mainController) throws IOException {

        TabPane tabPane = (TabPane) mainController.getRootLayout().getCenter();
        if (tabPane.getSelectionModel().isEmpty()) {
            mainController.getDialogController().showAlertDialog(Alert.AlertType.ERROR, "Ошибка", "Сначала откройте таблицу");
        } else {
            Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
            //Current viewed table
            TableView currentTable = (TableView) currentTab.getContent().lookup("#tableView");
            //Columns in table
            ObservableList<TableColumn> columns = currentTable.getColumns();
            //Users in table
            ObservableList<User> userList= currentTable.getItems();

            final int columnCount = columns.size();
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet(currentTab.getText());

            //Style for title
            CellStyle boldFont = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            boldFont.setFont(font);

            //creating titles with bold font
            Row row = sheet.createRow(0);
            for (int i = 0; i < columnCount; i++) {
                row.createCell(i).setCellValue(columns.get(i).getText());
                row.getCell(i).setCellStyle(boldFont);
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
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xls)","*.xls");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(mainController.getPrimaryStage());
            FileOutputStream fileOutputStream;
            if(file != null){
                fileOutputStream = new FileOutputStream(file);
                workbook.write(fileOutputStream);
                fileOutputStream.close();
                mainController.getDialogController().showAlertDialog(Alert.AlertType.INFORMATION,
                        "Завершено", "Файл сохранен" + "\n" + file.getAbsolutePath());
            }
            workbook.close();
        }

    }

}
