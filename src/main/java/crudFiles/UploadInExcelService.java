package crudFiles;


import controllers.DialogController;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import objects.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UploadInExcelService {

    public static void uploadInExcel(ObservableList<TableColumn> columns,
                                     ObservableList<User> userList,
                                     File file) throws IOException {


        final int columnCount = columns.size();
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Пользователи");

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

        //Save data in file
        FileOutputStream fileOutputStream;
        if(file != null){
            fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        }
        workbook.close();
    }

}


