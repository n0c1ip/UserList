package crudDB;

import objects.Location;
import org.junit.Assert;
import org.junit.Test;


public class BeanValidationTest {

    @Test
    public void ShouldConfirmDataCorrectness() {
        Location location = new Location("locationToTestValidation");
        Assert.assertTrue(BeanValidation.isCorrectData(location));
    }

    @Test
    public void ShouldDetectIncorrectData() {
        Location location = new Location();
        Assert.assertFalse(BeanValidation.isCorrectData(location));
    }

    @Test
    public void ShouldGetViolationsText() {
        Location location = new Location();
        Assert.assertFalse(BeanValidation.getViolationsText(location).isEmpty());
    }

    @Test
    public void ShouldGetEmptyViolationsText() {
        Location location = new Location("locationToTestValidation");
        Assert.assertTrue(BeanValidation.getViolationsText(location).isEmpty());
    }

}
