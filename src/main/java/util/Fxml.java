package util;

import javafx.fxml.FXMLLoader;
import start.EntryPoint;

public class Fxml {

    private Fxml(){}

    @SuppressWarnings({"HardcodedFileSeparator"})
    public static FXMLLoader getFXMLLoader(String fileName) {
        return new FXMLLoader(EntryPoint.class.getResource("/fxml/" + fileName));
    }

}
