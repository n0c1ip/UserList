package util;

import javafx.scene.image.Image;

public class Icons {

    private Icons() {}

    private static Image createImageObject(String imageFile) {
        String folder = "icons";
        return new Image(folder + "/" + imageFile);
    }

    public static Image getApplicationIcon() {
        return createImageObject("application-icon.png");
    }

    public static Image getUserIcon() {
        return createImageObject("user-icon.png");
    }

    public static Image getOrganizationIcon() {
        return createImageObject("organization-icon.png");
    }

    public static Image getDepartmentIcon() {
        return createImageObject("department-icon.png");
    }

    public static Image getLocationIcon() {
        return createImageObject("location-icon.png");
    }

    public static Image getImportIcon() {
        return createImageObject("import-icon.png");
    }

    public static Image getSettingsIcon() {
        return createImageObject("settings-icon.png");
    }

}
