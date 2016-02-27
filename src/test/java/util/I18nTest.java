package util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ResourceBundle;

public class I18nTest {

    @Test
    public void shouldReturnCorrectBundle() {
        ResourceBundle resourceBundle = I18n.DIALOG.getResourceBundle();
        Assert.assertNotNull(resourceBundle);
        Assert.assertNotNull(resourceBundle.getLocale());
        Assert.assertFalse(resourceBundle.keySet().isEmpty());
    }

    @Test
    public void shouldReturnError() {
        String errorString = I18n.DIALOG.getString("dsd");
        Assert.assertEquals("err#", errorString);
    }

    @Test
    public void shouldReturnValue() {
        String errorString = I18n.DIALOG.getString("Title.AddUser");
        Assert.assertNotNull(errorString);
        Assert.assertFalse(errorString.isEmpty());
    }


}