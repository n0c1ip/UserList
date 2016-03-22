package crudDB;

import objects.ExtendedRevisionEntity;
import objects.Location;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;


public class ExtendedRevisionServiceTest {

    @Test
    public void ShouldGetLastRevisionEntity() {
        Location location = new Location("TestRevisionEntity");

        Location addedLocation = LocationService.add(location);
        ExtendedRevisionEntity revisionEntity = ExtendedRevisionService.getLastRevisionEntity(Location.class, addedLocation);

        long expectedTime = new Date().getTime();
        long actualTime = revisionEntity.getRevisionDate().getTime();
        long diffInSeconds = (expectedTime - actualTime) / 1000;

        try {
            Assert.assertNotNull(revisionEntity);
            Assert.assertNotNull(revisionEntity.getId());
            Assert.assertNotNull(revisionEntity.getTimestamp());
            Assert.assertNotNull(revisionEntity.getUserName());
            Assert.assertNotNull(revisionEntity.getRevisionDate());
            Assert.assertEquals(System.getProperty("user.name"), revisionEntity.getUserName());
            Assert.assertTrue(diffInSeconds < 1);
        } finally {
            LocationService.delete(addedLocation.getId());
        }
    }

    @Test
    public void ShouldGetLastRevision() {
        Location location = new Location("TestRevision");
        location.setAddress("TestAddress");
        location.setCity("TestCity");

        Location addedLocation = LocationService.add(location);
        Location revisionLocation = ExtendedRevisionService.getLastRevision(Location.class, addedLocation);

        try {
            Assert.assertEquals(addedLocation.getId(), revisionLocation.getId());
            Assert.assertEquals(addedLocation.getName(), revisionLocation.getName());
            Assert.assertEquals(addedLocation.getAddress(), revisionLocation.getAddress());
            Assert.assertEquals(addedLocation.getCity(), revisionLocation.getCity());
        } finally {
            LocationService.delete(addedLocation.getId());
        }
    }

}