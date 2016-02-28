package util;


import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

public class IconsTest extends GuiTest {

    @Override
    protected Parent getRootNode() {
        return new BorderPane();
    }

    @Test
    public void shouldReturnImage(){
        Image image = Icons.getApplicationIcon();
        Assert.assertNotNull(image);
        Assert.assertNotNull(image.getHeight());
    }


}
