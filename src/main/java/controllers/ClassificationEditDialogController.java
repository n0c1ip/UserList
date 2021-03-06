package controllers;

import crudDB.BeanValidation;
import crudDB.ClassificationService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.Classification;

public class ClassificationEditDialogController {

    @FXML
    private TextField nameField;
    private Classification editedClassification;

    public void setEditedClassification(Classification editedClassification) {
        this.editedClassification = editedClassification;
        nameField.setText(editedClassification.getName());
    }

    public void handleOkButton() {
        editedClassification.setName(nameField.getText());
        if (BeanValidation.isCorrectData(editedClassification)) {
            ClassificationService.add(editedClassification);
            closeWindow();
        } else {
            DialogController.showErrorDialog(BeanValidation.getViolationsText(editedClassification));
        }
    }

    public void handleCancelButton() {
        closeWindow();
    }

    private void closeWindow(){
        Stage thisWindow = (Stage) nameField.getScene().getWindow();
        thisWindow.close();
    }
}
