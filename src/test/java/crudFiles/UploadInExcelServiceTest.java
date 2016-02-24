package crudFiles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import objects.Department;
import objects.Location;
import objects.Pc;
import objects.User;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

public class UploadInExcelServiceTest {


    @Test
    public void shouldWriteDataToFile() throws Exception {

        User testUser = new User();
        testUser.setFirstName("FirstName");
        testUser.setLastName("LastName");
        testUser.setMiddleName("MiddleName");
        testUser.setLocation(new Location("TestLocation"));
        testUser.setPc(new Pc("TestPC"));
        testUser.setDepartment(new Department("TestDepartment"));
        testUser.setPosition("TestPosition");
        testUser.setLogin("TestLogin");
        testUser.setMail("TestMail");
        testUser.setPassword("TestPassword");
        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(testUser);
        ObservableList<TableColumn> columns = FXCollections.observableArrayList();
        for (int i = 0; i < 8; i++) {
            columns.add(new TableColumn("TestColumn"+i));
        }

        File file = new File("testXLs.xls");

        try {
            UploadInExcelService.uploadInExcel(columns,users,file);
        } catch (ExceptionInInitializerError e){
        }

        FileInputStream fileInputStream = new FileInputStream(file);
        StringBuilder builder = new StringBuilder();
        int getChar;
        while((getChar = fileInputStream.read()) != -1){
            builder.append((char)getChar);
        }
        String finalString = builder.toString();
        fileInputStream.close();

        Assert.assertTrue(finalString.contains("FirstName"));
        Assert.assertTrue(finalString.contains("LastName"));
        Assert.assertTrue(finalString.contains("MiddleName"));
        Assert.assertTrue(finalString.contains("TestDepartment"));
        Assert.assertTrue(finalString.contains("TestPosition"));
        Assert.assertTrue(finalString.contains("TestLogin"));
        Assert.assertTrue(finalString.contains("TestMail"));
        Assert.assertTrue(finalString.contains("TestPassword"));

        Files.deleteIfExists(file.toPath());

    }


}

