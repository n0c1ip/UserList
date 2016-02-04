package controllers;

import com.opencsv.CSVReader;
import interfaces.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.User;
import start.EnterPoint;
import util.ListUtil;

import java.io.*;

/**
 * <p>
 * Class controller for import Users from CSV file.</p>
 *
 */

public class ImportCSVController implements Dialog{

    private EnterPoint enterPoint;
    private Stage dialog;
    private String filePath;
    @FXML
    ChoiceBox<String> choiceBox;
    @FXML
    TextField filePathField;


    /**
     * Loading list of tables in ChoiceBox,
     * Set TextField not editable
     */
    @FXML
    private void initialize(){
        ObservableList<String> stringBox = FXCollections.observableArrayList();
        stringBox.addAll(ListUtil.getMapStrings());
        choiceBox.setItems(stringBox);
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
        File selectedFile = fileChooser.showOpenDialog(enterPoint.getPrimaryStage());
        if(selectedFile != null){
            filePath = selectedFile.getAbsolutePath();
            filePathField.setText(filePath);
        }
    }

    public void setEnterPoint(EnterPoint enterPoint) {
        this.enterPoint = enterPoint;
    }
    public void setDialog(Stage dialog) {
        this.dialog = dialog;
    }

    /**
     * Loading Users from csv file to selected table
     * <p>
     * CSV file format:
     * First Name; Last Name; Middle Name; Department Name; Position; Login; Password; E-Mail
     * @param tablename table to load Users
     * @param csvfilename - path to csv file
     * @param delimiter - delimeter used in csv file
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void loadUsersFromCSV(String tablename, String csvfilename, char delimiter) throws IOException {

        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(csvfilename), "windows-1251"), delimiter);
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            ListUtil.getListByName(tablename).add(new User(nextLine[0], nextLine[1], nextLine[2],
                    nextLine[3], nextLine[4], nextLine[5], nextLine[6], nextLine[7]));
        }
        reader.close();

    }

    public void handleLoadButton() {
       if (!filePathField.getText().isEmpty() && choiceBox.getSelectionModel().getSelectedItem() != null){
           try {
               loadUsersFromCSV(choiceBox.getSelectionModel().getSelectedItem(),filePathField.getText(),';');
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Импорт из CSV");
               alert.setHeaderText(null);
               alert.setContentText("Импорт завершен");
               alert.showAndWait();
           } catch (IOException e) {
               if(e instanceof FileNotFoundException){
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Ошибка импорта");
                   alert.setHeaderText(null);
                   alert.setContentText("Файл не найден");
                   alert.showAndWait();
               }
               e.printStackTrace();
           } finally {
               dialog.close();
           }


       } else {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Ошибка файла");
           alert.setHeaderText(null);
           alert.setContentText("Выбирите файл");
           alert.showAndWait();
       }


    }


    public void handleCancelButton(ActionEvent actionEvent) {
        this.dialog.close();
    }
}
