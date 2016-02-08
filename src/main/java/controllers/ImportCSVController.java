package controllers;

import crud.LocationService;
import interfaces.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.Location;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 * Class controller for import Users from CSV file.</p>
 *
 */

public class ImportCSVController implements Dialog{

    private MainController mainController;
    private Stage dialog;
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
    public void setDialog(Stage dialog) {
        this.dialog = dialog;
    }

    /**
     * Loading Users from csv file to selected table
     * <p>
     * CSV file format:
     * First Name; Last Name; Middle Name; Department Name; Position; Login; Password; E-Mail
     * @param location location to load Users
     * @param csvfilename - path to csv file
     * @param delimiter - delimeter used in csv file
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public void loadUsersFromCSV(Location location, String csvfilename, char delimiter) throws IOException {


    }

    public void handleLoadButton() {
        if (!filePathField.getText().isEmpty() && choiceBox.getSelectionModel().getSelectedItem() != null){
            try {
                loadUsersFromCSV(choiceBox.getSelectionModel().getSelectedItem(),filePathField.getText(),';');
                mainController.getDialogController().showAlertDialog(Alert.AlertType.INFORMATION,"Импорт из CSV","Импорт завершен");
            } catch (FileNotFoundException e) {
                mainController.getDialogController().showAlertDialog(Alert.AlertType.ERROR,"Ошибка импорта","Файл не найден");
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                mainController.getDialogController().showAlertDialog(Alert.AlertType.ERROR,"Ошибка импорта","Неверная кодировка файла");
            } catch (IOException e){
                mainController.getDialogController().showAlertDialog(Alert.AlertType.ERROR,"Ошибка импорта","Ошибка");
            } finally {
                dialog.close();
            }

        } else {
            mainController.getDialogController().showAlertDialog(Alert.AlertType.ERROR,"Ошибка файла","Выбирите файл");
        }
    }

    public void handleCancelButton() {
        this.dialog.close();
    }
}
