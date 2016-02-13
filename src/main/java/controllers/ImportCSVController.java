package controllers;

import com.opencsv.CSVReader;
import crud.DepartmentService;
import crud.LocationService;
import crud.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.Department;
import objects.Location;
import objects.User;

import javax.persistence.NoResultException;
import java.io.*;

/**
 * <p>
 * Class controller for import Users from CSV file.</p>
 *
 */

public class ImportCSVController {

    private MainController mainController;
    private String filePath;
    @FXML
    ChoiceBox<Location> choiceBox;
    @FXML
    TextField filePathField;


    /**
     * Loading list of tables in ChoiceBox,
     * Set TextField not editable
     */
    @FXML
    private void initialize(){
        ObservableList<Location> stringBox = FXCollections.observableArrayList();
        stringBox.addAll(LocationService.getAll());
        choiceBox.setItems(stringBox.sorted());
        choiceBox.getSelectionModel().selectFirst();
        filePathField.setEditable(false);
    }

    /**
     * Dialog to choose file
     */
    @FXML
    private void chooseCsvFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(mainController.getPrimaryStage());
        if(selectedFile != null){
            filePath = selectedFile.getAbsolutePath();
            filePathField.setText(filePath);
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Loading Users from csv file to selected table
     * <p>
     * CSV file format:
     * Last Name; First Name; Middle Name; Department Name; Position; Login; Password; E-Mail
     * @param location location to load Users
     * @param fileInputStream - csv data input stream
     * @param delimiter - delimeter used in csv
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public void loadUsersFromCSV(Location location, InputStream fileInputStream, char delimiter) throws IOException {
        CSVReader reader = new CSVReader(new InputStreamReader(fileInputStream, "windows-1251"), delimiter);
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine.length == 8){
                User userToAdd = new User();

                userToAdd.setLastName(nextLine[0]);
                userToAdd.setFirstName(nextLine[1]);
                userToAdd.setMiddleName(nextLine[2]);
                userToAdd.setLocation(location);
                Department department = new Department(nextLine[3]);
                try{
                    department = DepartmentService.getByName(nextLine[3]);
                } catch (NoResultException e){
                    department = DepartmentService.add(department);
                } finally {
                    userToAdd.setDepartment(department);
                }
                userToAdd.setPosition(nextLine[4]);
                userToAdd.setLogin(nextLine[5]);
                userToAdd.setPassword(nextLine[6]);
                userToAdd.setMail(nextLine[7]);
                UserService.add(userToAdd);
            }
        }
        reader.close();
    }

    public void handleLoadButton() {
        if (!filePathField.getText().isEmpty() && choiceBox.getSelectionModel().getSelectedItem() != null){
            try {
                loadUsersFromCSV(choiceBox.getSelectionModel().getSelectedItem(), new FileInputStream(filePathField.getText()),';');
                mainController.getDialogController().showAlertDialog(Alert.AlertType.INFORMATION,"Импорт из CSV","Импорт завершен");
            } catch (FileNotFoundException e) {
                mainController.getDialogController().showAlertDialog(Alert.AlertType.ERROR,"Ошибка импорта","Файл не найден");
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                mainController.getDialogController().showAlertDialog(Alert.AlertType.ERROR,"Ошибка импорта","Неверная кодировка файла");
            } catch (IOException e){
                mainController.getDialogController().showAlertDialog(Alert.AlertType.ERROR,"Ошибка импорта","Ошибка");
            } finally {
                closeWindow();
            }

        } else {
            mainController.getDialogController().showAlertDialog(Alert.AlertType.ERROR,"Ошибка файла","Выбирите файл");
        }
    }

    public void handleCancelButton() {
        closeWindow();
    }

    private void closeWindow(){
        Stage thisWindow = (Stage) choiceBox.getScene().getWindow();
        thisWindow.close();
    }
}
