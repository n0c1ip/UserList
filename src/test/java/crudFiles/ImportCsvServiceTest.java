package crudFiles;

import controllers.ImportCSVController;
import crudDB.DepartmentService;
import crudDB.LocationService;
import crudDB.UserService;
import org.junit.Assert;
import objects.Location;
import objects.User;
import org.junit.Test;

import java.io.*;
import java.util.List;

public class ImportCsvServiceTest {


    @Test(expected = FileNotFoundException.class)
    public void shouldThrowFileNotFoundException() throws IOException {
        Location location = new Location("newLocation");
        ImportCSVService.loadUsersFromCSV(location, new FileInputStream("thereIsNoFile"),';');
    }

    @Test
    public void shouldLoadUsersFromCSV() throws IOException {
        String locationName = "LocationToTestCSVImport";
        Location location = new Location(locationName);
        Location addedLocation = LocationService.add(location);

        String userLastName = "LastName";
        String userFirstName = "FirstName";
        String userMiddleName = "MiddleName";
        String userDepartmentName = "DepartmentNameToTestCsvImport";
        String userPosition = "Position";
        String userLogin = "Login";
        String userPassword = "P4SSW0RD";
        String userMail = "mmail@7r.perm.ru";
        String csvLine = userLastName + ";" + userFirstName + ";"  + userMiddleName + ";" + userDepartmentName + ";" +
                         userPosition + ";" + userLogin + ";" + userPassword + ";" + userMail;
        InputStream inputStream = new ByteArrayInputStream(csvLine.getBytes());

        ImportCSVService.loadUsersFromCSV(addedLocation, inputStream, ';');

        List<User> userList = UserService.getUsersByLocation(addedLocation);
        User user = userList.get(0);

        try {
            Assert.assertEquals(userLastName, user.getLastName());
            Assert.assertEquals(userFirstName, user.getFirstName());
            Assert.assertEquals(userMiddleName, user.getMiddleName());
            Assert.assertEquals(userPosition, user.getPosition());
            Assert.assertEquals(userLogin, user.getLogin());
            Assert.assertEquals(userPassword, user.getPassword());
            Assert.assertEquals(userMail, user.getMail());
            Assert.assertEquals(locationName, user.getLocation().getName());
            Assert.assertEquals(userDepartmentName, user.getDepartment().getName());
        } finally {
           UserService.delete(user.getId());
           LocationService.delete(user.getLocation().getId());
           DepartmentService.delete(user.getDepartment().getId());
        }

    }

}
