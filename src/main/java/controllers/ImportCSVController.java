package controllers;

import crudDB.OrganizationService;
import crudFiles.ImportCSVService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.Organization;
import util.ActiveUser;
import util.Permission;

import java.io.*;

/**
 * <p>
 * Class controller for import Users from CSV file.</p>
 *
 */
public class ImportCSVController {

    private MainController mainController;
    @FXML
    private Button choiceLoad;
    @FXML
    private ChoiceBox<Organization> choiceBox;
    @FXML
    private TextField filePathField;


    /**
     * Loading organization in ChoiceBox,
     * Set TextField not editable
     */
    @FXML
    private void initialize(){
        if (!ActiveUser.hasPermission(Permission.WRITE)) {
            choiceLoad.setDisable(true);
        }

        ObservableList<Organization> organizations = FXCollections.observableArrayList();
        organizations.addAll(OrganizationService.getAll());
        choiceBox.setItems(organizations.sorted());
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
            String filePath = selectedFile.getAbsolutePath();
            filePathField.setText(filePath);
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    public void handleLoadButton() {
        if (!filePathField.getText().isEmpty() && choiceBox.getSelectionModel().getSelectedItem() != null){
            try {
                ImportCSVService.loadUsersFromCSV(choiceBox.getSelectionModel().getSelectedItem(), new FileInputStream(filePathField.getText()), ';');
                DialogController.showAlertDialog(Alert.AlertType.INFORMATION,"Импорт из CSV","Импорт завершен");
            } catch (FileNotFoundException e) {
                DialogController.showErrorDialog("Файл не найден");
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                DialogController.showErrorDialog("Неверная кодировка файла");
            } catch (IOException e){
                DialogController.showErrorDialog("Ошибка");
            } finally {
                closeWindow();
            }

        } else {
            DialogController.showErrorDialog("Выбирите файл");
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
