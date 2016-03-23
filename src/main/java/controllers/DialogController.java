package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import objects.*;
import util.Fxml;
import util.I18n;
import util.Icons;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Controller for Dialog windows
 */
public class DialogController {

    private Stage primaryStage;
    private MainController mainController;


    public void showExistingUserChoiceDialog(String title, Location location) {
        getExistingUserChoiceDialog(title, location).showAndWait();
    }
    public Stage getExistingUserChoiceDialog(String title, Location location) {
        final Stage dialog = new Stage();
        try {
            dialog.setTitle(title);
            dialog.getIcons().add(Icons.getUserIcon());
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = Fxml.getFXMLLoader("existingUserChoiceTable.fxml");
            loader.setResources(I18n.TABLE.getResourceBundle());
            SplitPane useredit = loader.load();
            ExistingUserChoiceController controller = loader.getController();
            controller.setLocation(location);
            dialog.setScene(new Scene(useredit));
            return dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showErrorDialog("Не удалось загрузить интерфейс выбора способа добавления пользователя");
        }
        return dialog;
    }

    public void showExistingUserInDepartmentChoiceDialog(String title, Classification classification){
        getExistingUserInDepartmentChoiceDialog(title, classification).showAndWait();
    }
    public Stage getExistingUserInDepartmentChoiceDialog(String title, Classification classification){
        final Stage dialog = new Stage();
        try {
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(mainController.getPrimaryStage());
            FXMLLoader loader = Fxml.getFXMLLoader("userChoiceDialog.fxml");
            loader.setResources(I18n.TABLE.getResourceBundle());
            SplitPane table = loader.load();
            UserChoiceController controller = loader.getController();
            controller.setClassification(classification);
            dialog.setScene(new Scene(table));
            return dialog;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dialog;
    }

    public void showExistingUserInDepartmentChoiceDialog(String title, Pc pc){
        getExistingUserInDepartmentChoiceDialog(title,pc).showAndWait();
    }
    public Stage getExistingUserInDepartmentChoiceDialog(String title, Pc pc){
        final Stage dialog = new Stage();
        try {
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(mainController.getPrimaryStage());
            FXMLLoader loader = Fxml.getFXMLLoader("userChoiceDialog.fxml");
            loader.setResources(I18n.TABLE.getResourceBundle());
            SplitPane table = loader.load();
            UserChoiceController controller = loader.getController();
            controller.setPC(pc);
            dialog.setScene(new Scene(table));
            return dialog;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dialog;
    }

    public void showNewUserMethodChoiceDialog(Location location) {
        getNewUserMethodChoiceDialog(location).showAndWait();
    }
    public Stage getNewUserMethodChoiceDialog(Location location) {
        final Stage dialog = new Stage();
        try{
            dialog.setTitle(I18n.DIALOG.getString("Title.UserMethodAdd"));
            dialog.getIcons().add(Icons.getUserIcon());
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = Fxml.getFXMLLoader("userMethodChoiceDialog.fxml");
            loader.setResources(I18n.DIALOG.getResourceBundle());
            AnchorPane useredit = loader.load();
            UserMethodChoiceController controller = loader.getController();
            controller.setLocation(location);
            controller.setDialogController(this);
            dialog.setScene(new Scene(useredit));
            return  dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showErrorDialog("Не удалось загрузить интерфейс выбора способа добавления пользователя");
        }
        return dialog;
    }

    public void showNetworkEditDialog(String title, Network network) {
        getNetworkEditDialog(title, network).showAndWait();
    }
    public Stage getNetworkEditDialog(String title, Network network){
        final Stage dialog = new Stage();
        try{
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = Fxml.getFXMLLoader("networkEditDialog.fxml");
            loader.setResources(I18n.DIALOG.getResourceBundle());
            AnchorPane networkEdit = loader.load();
            NetworkEditController controller = loader.getController();
            controller.setEditedNetwork(network);
            dialog.setScene(new Scene(networkEdit));
            return  dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            showErrorDialog("Не удалось загрузить интерфейс редактирования сети");
        }
        return dialog;
    }

    public void showVlanEditDialog(String title, Vlan vlan) {
        getVlanEditDialog(title, vlan).showAndWait();
    }
    private Stage getVlanEditDialog(String title, Vlan vlan) {
        final Stage dialog = new Stage();
        try{
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = Fxml.getFXMLLoader("vlanEditDialog.fxml");
            loader.setResources(I18n.DIALOG.getResourceBundle());
            AnchorPane vlanEdit = loader.load();
            VlanEditController controller = loader.getController();
            controller.setEditedVlan(vlan);
            dialog.setScene(new Scene(vlanEdit));
            return  dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            showErrorDialog("Не удалось загрузить интерфейс редактирования VLAN");
        }
        return dialog;
    }

    public void showUserEditDialog(String title, User user) {
        getUserEditDialog(title, user).showAndWait();
    }
    public Stage getUserEditDialog(String title, User user) {
        final Stage dialog = new Stage();
        try{
            dialog.setTitle(title);
            dialog.getIcons().add(Icons.getUserIcon());
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = Fxml.getFXMLLoader("userEditDialog.fxml");
            loader.setResources(I18n.DIALOG.getResourceBundle());
            AnchorPane useredit = loader.load();
            UserEditController controller = loader.getController();
            controller.setDialogController(this);
            controller.setEditedUser(user);
            dialog.setScene(new Scene(useredit));
            return  dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showErrorDialog("Не удалось загрузить интерфейс редактирования пользователя");
        }
        return dialog;
    }

    public void showPcEditDialog(String title, Pc pc) {
        getPcEditDialog(title, pc).showAndWait();
    }
    public Stage getPcEditDialog(String title, Pc pc) {
        final Stage dialog = new Stage();
        try{
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = Fxml.getFXMLLoader("pcEditDialog.fxml");
            loader.setResources(I18n.DIALOG.getResourceBundle());
            AnchorPane pcEdit = loader.load();
            PcEditController controller = loader.getController();
            controller.setDialogController(this);
            controller.setEditedPc(pc);
            dialog.setScene(new Scene(pcEdit));
            return  dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showErrorDialog("Не удалось загрузить интерфейс редактирования компьютера");
        }
        return dialog;
    }

    public void showPServerEditDialog(String title, PhysicalServer physicalServer) {
        getPServerEditDialog(title,physicalServer).showAndWait();
    }
    public Stage getPServerEditDialog(String title,PhysicalServer physicalServer){
        final Stage dialog = new Stage();
        try{
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = Fxml.getFXMLLoader("pServerEditDialog.fxml");
            loader.setResources(I18n.DIALOG.getResourceBundle());
            AnchorPane pServerEdit = loader.load();
            PhysicalServerEditController controller = loader.getController();
            controller.setEditedServer(physicalServer);
            dialog.setScene(new Scene(pServerEdit));
            return  dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showErrorDialog("Не удалось загрузить интерфейс редактирования сервера");
        }
        return dialog;
    }



    public void showSignUnlimitedEditDialog(String title, SignUnlimited signUnlimited) {
        getSignUnlimitedEditDialog(title, signUnlimited).showAndWait();
    }
    public Stage getSignUnlimitedEditDialog(String title, SignUnlimited signUnlimited) {
        final Stage dialog = new Stage();
        try{
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = Fxml.getFXMLLoader("signUnlimitedEditDialog.fxml");
            loader.setResources(I18n.DIALOG.getResourceBundle());
            AnchorPane useredit = loader.load();
            SignUnlimitedEditController controller = loader.getController();
            controller.setEditedSignUnlimited(signUnlimited);
            dialog.setScene(new Scene(useredit));
            return  dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showErrorDialog("Не удалось загрузить интерфейс редактирования признака");
        }
        return dialog;
    }

    public void showClassificationEditDialog(String title, Classification classification) {
        getClassificationEditDialog(title, classification).showAndWait();
    }
    public Stage getClassificationEditDialog(String title, Classification classification) {
        final Stage dialog = new Stage();
        try{
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = Fxml.getFXMLLoader("classificationEditDialog.fxml");
            AnchorPane classificationEdit = loader.load();
            ClassificationEditDialogController controller = loader.getController();
            controller.setEditedClassification(classification);
            dialog.setScene(new Scene(classificationEdit));
            return  dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showErrorDialog("Не удалось загрузить интерфейс редактирования классификатора");
        }
        return dialog;
    }

    public void showUserSignUnlimitedEditDialog(String title, UserSignUnlimited userSignUnlimited) {
        getUserSignUnlimitedEditDialog(title, userSignUnlimited).showAndWait();
    }
    public Stage getUserSignUnlimitedEditDialog(String title, UserSignUnlimited userSignUnlimited) {
        final Stage dialog = new Stage();
        try{
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = Fxml.getFXMLLoader("userSignUnlimitedEdit.fxml");
            loader.setResources(I18n.DIALOG.getResourceBundle());
            AnchorPane userSignUnlimitedPane = loader.load();
            UserSignUnlimitedEditController controller = loader.getController();
            controller.setEditedUserSignUnlimited(userSignUnlimited);
            dialog.setScene(new Scene(userSignUnlimitedPane));
            return  dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showErrorDialog("Не удалось загрузить интерфейс признаков пользователя");
        }
        return dialog;
    }

    public void showUserSignUnlimitedTableDialog(String title, User user) {
        getUserSignUnlimitedTableDialog(title, user).showAndWait();
    }
    public Stage getUserSignUnlimitedTableDialog(String title, User user) {
        final Stage dialog = new Stage();
        try{
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = Fxml.getFXMLLoader("userSignUnlimitedTable.fxml");
            loader.setResources(I18n.TABLE.getResourceBundle());
            SplitPane userSignUnlimited = loader.load();
            UserSignUnlimitedTableController controller = loader.getController();
            controller.setUser(user);
            controller.showUserUnlimitedSigns();
            controller.setMainController(mainController);
            dialog.setScene(new Scene(userSignUnlimited));
            return  dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showErrorDialog("Не удалось загрузить интерфейс признаков пользователя");
        }
        return dialog;
    }

    public void showOrganizationEditDialog(String title, Organization organization) {
        getOrganizationEditDialog(title, organization).showAndWait();
    }
    public Stage getOrganizationEditDialog(String title, Organization organization) {
        final Stage dialog = new Stage();
        try{
            dialog.setTitle(title);
            dialog.getIcons().add(Icons.getOrganizationIcon());
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = Fxml.getFXMLLoader("organizationEditDialog.fxml");
            loader.setResources(I18n.DIALOG.getResourceBundle());
            AnchorPane useredit = loader.load();
            OrganizationEditController controller = loader.getController();
            controller.setEditedOrganization(organization);
            dialog.setScene(new Scene(useredit));
            return  dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showErrorDialog("Не удалось загрузить интерфейс редактирования организации");
        }
        return dialog;
    }

    public void showDepartmentEditDialog(String title, Department department, Organization organization) {
        getDepartmentEditDialog(title, department, organization).showAndWait();
    }
    public Stage getDepartmentEditDialog(String title, Department department, Organization organization) {
        final Stage dialog = new Stage();
        dialog.getIcons().add(Icons.getDepartmentIcon());
        try{
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = Fxml.getFXMLLoader("departmentEditDialog.fxml");
            loader.setResources(I18n.DIALOG.getResourceBundle());
            AnchorPane useredit = loader.load();
            DepartmentEditController controller = loader.getController();
            controller.setActiveOrganization(organization);
            controller.setEditedDepartment(department);
            dialog.setScene(new Scene(useredit));
            return  dialog;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showErrorDialog("Не удалось загрузить интерфейс редактирования пользователя");
        }
        return dialog;
    }

    public void showLocationEditDialog(String title, Location location){
        getLocationEditDialog(title, location).showAndWait();
    }
    public Stage getLocationEditDialog(String title, Location location) {
        final Stage dialog = new Stage();
        dialog.getIcons().add(Icons.getLocationIcon());
        try{
            dialog.setTitle(title);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = Fxml.getFXMLLoader("locationEditDialog.fxml");
            loader.setResources(I18n.DIALOG.getResourceBundle());
            AnchorPane locationEdit = loader.load();
            LocationEditController controller = loader.getController();
            controller.setEditedLocation(location);
            dialog.setScene(new Scene(locationEdit));
            return dialog;
        } catch (IOException e){
            System.out.println(e.getMessage());
            showErrorDialog("Не удалось загрузить интерфейс редактирования объектов");
        }
        return dialog;
    }

    public static void showErrorDialog(String contentText){
        getAlertDialog(Alert.AlertType.ERROR, I18n.ERROR.getString("Error"), contentText).showAndWait();
    }
    public static void showAlertDialog(Alert.AlertType alertType, String dialogTitle, String contentText){
        getAlertDialog(alertType, dialogTitle, contentText).showAndWait();
    }
    public static Alert getAlertDialog(Alert.AlertType alertType, String dialogTitle, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(dialogTitle);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        return alert;
    }

    public static void showLastEditDialog(String userName, Date editDate){
        getLastEditDialog(userName, editDate).showAndWait();
    }
    public static Alert getLastEditDialog(String userName, Date editDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy' 'HH:mm:ss");
        String formattedDate = simpleDateFormat.format(editDate);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(I18n.DIALOG.getString("Dialog.LastEditHeader"));
        alert.setHeaderText(null);
        alert.setContentText(I18n.DIALOG.getString("Dialog.LastEditUser") + ": " + userName + "\n" +
                        I18n.DIALOG.getString("Dialog.LastEditDate") + ": " + formattedDate);
        return alert;
    }

    public Stage getLoginDialog(){
        final Stage dialog = new Stage();
        try{
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setResizable(false);
            FXMLLoader loader = Fxml.getFXMLLoader("login.fxml");
            AnchorPane login = loader.load();
            dialog.setScene(new Scene(login));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showErrorDialog("Не удалось загрузить интерфейс логина");
        }
        return dialog;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public File showFileSaveDialog(String descriptionExtension, String extension){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(descriptionExtension,extension);
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showSaveDialog(primaryStage);
    }


}
