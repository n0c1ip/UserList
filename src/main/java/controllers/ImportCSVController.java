package controllers;
//Created by mva on 01.02.2016.

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import start.EnterPoint;
import util.ListUtil;

import java.io.File;

public class ImportCSVController {

    private EnterPoint enterPoint;
    private Stage dialog;
    private String filePath;
    @FXML
    ChoiceBox<String> choiceBox;
    @FXML
    TextField filePathField;


    @FXML
    private void initialize(){
        ObservableList<String> stringBox = FXCollections.observableArrayList();
        stringBox.addAll(ListUtil.getMapStrings());
        choiceBox.setItems(stringBox);
        choiceBox.getSelectionModel().selectFirst();
        filePathField.setEditable(false);
    }

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

    public void handleOpenButton(ActionEvent actionEvent) {
       if (filePathField != null && choiceBox.getSelectionModel().getSelectedItem() != null){
            ListUtil.loadUsersFromCSV(choiceBox.getSelectionModel().getSelectedItem(),filePathField.getText(),';');
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Import CSV");
           alert.setHeaderText(null);
           alert.setContentText("Импорт завершен");
           alert.showAndWait();
           dialog.close();
       }

    }

    public void handleCancelButton(ActionEvent actionEvent) {
        this.dialog.close();
    }
}
