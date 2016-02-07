package crud;

import objects.Location;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class LocationServiceTest {

    @Test
    public void ShouldAddLocationService() {
        Location location = new Location("locationToAdd");

        Location addedLocation = LocationService.add(location);
        Location foundLocation = LocationService.get(addedLocation.getId());

        try {
            Assert.assertNotNull(addedLocation);
            Assert.assertNotNull(foundLocation);
            Assert.assertEquals(addedLocation.getId(), foundLocation.getId());
        } finally {
            LocationService.delete(addedLocation.getId());
        }
    }

    @Test
    public void ShouldDeleteLocationService() {
        Location location = new Location("locationToDelete");

        Location addedLocation = LocationService.add(location);
        long addedLocationId = addedLocation.getId();
        LocationService.delete(addedLocationId);

        Assert.assertNull(LocationService.get(addedLocationId));
    }

    @Test
    public void ShouldUpdateLocationService() {
        String oldName = "locationToUpdate";
        String newName = "updatedLocation";
        Location location = new Location();

        location.setName(oldName);
        Location addedLocation = LocationService.add(location);

        addedLocation.setName(newName);
        LocationService.update(addedLocation);
        Location foundLocation = LocationService.get(addedLocation.getId());

        try {
            Assert.assertEquals(foundLocation.getName(), newName);
        } finally {
            LocationService.delete(foundLocation.getId());
        }
    }

    @Test
    public void ShouldGetByNameLocationService() {
        Location location = new Location("locationToGetByName");

        Location addedLocation = LocationService.add(location);
        Location gottenLocation = LocationService.getByName(addedLocation.getName());

        try {
            Assert.assertNotNull(gottenLocation);
            Assert.assertEquals(location.getName(), gottenLocation.getName());
        } finally {
                LocationService.delete(addedLocation.getId());
        }
    }

    @Test
    public void ShouldGetAllLocationServices() {
        int expectedLocationsCount = 3;
        String name1 = "locationOne";
        String name2 = "locationTwo";
        String name3 = "locationThree";

        List<String> namesList = Arrays.asList(name1, name2, name3);

        Location addedLocation1 = LocationService.add(new Location(name1));
        Location addedLocation2 = LocationService.add(new Location(name2));
        Location addedLocation3 = LocationService.add(new Location(name3));

        int locationsFound = 0;
        List<Location> locationList = LocationService.getAll();
        for (Location location : locationList) {
            if (namesList.contains(location.getName())) {
                locationsFound++;
            }
        }

        try {
            Assert.assertEquals(expectedLocationsCount, locationsFound);
        } finally {
            LocationService.delete(addedLocation1.getId());
            LocationService.delete(addedLocation2.getId());
            LocationService.delete(addedLocation3.getId());
        }
    }




}