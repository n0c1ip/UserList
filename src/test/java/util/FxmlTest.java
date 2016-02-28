package util;

import javafx.fxml.FXMLLoader;
import org.junit.Assert;
import org.junit.Test;


public class FxmlTest {

    @Test
    public void shouldReturnWorkingLoader() {
        FXMLLoader loader = Fxml.getFXMLLoader("root.fxml");
        Assert.assertNotNull(loader);
        Assert.assertNotNull(loader.getLocation());
    }

    @Test
    public void shouldNotInitializeNonexistentLocation() {
        FXMLLoader loader = Fxml.getFXMLLoader("fxmlUnexistantFile");
        Assert.assertNotNull(loader);
        Assert.assertNull(loader.getLocation());
    }

}