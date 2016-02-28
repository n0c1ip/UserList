package crudFiles;

import crudDB.*;
import objects.Organization;
import objects.User;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.List;

public class ImportCsvServiceTest {


    @Test(expected = FileNotFoundException.class)
    public void shouldThrowFileNotFoundException() throws IOException {
        Organization organization = new Organization("newOrg");
        ImportCSVService.loadUsersFromCSV(organization, new FileInputStream("thereIsNoFile"),';');
    }

    @Test
    public void shouldLoadUsersFromCSV() throws IOException {
        String userOrganizationName = "OrganizationToTestCSVImport";
        Organization organization = new Organization(userOrganizationName);
        Organization addedOrganzation = OrganizationService.add(organization);

        String userLastName = "LastName";
        String userFirstName = "FirstName";
        String userMiddleName = "MiddleName";
        String userLocationName = "LocationNameToTestCsvImport";
        String userDepartmentName = "DepartmentNameToTestCsvImport";
        String userPosition = "Position";
        String userPc = "pcTest";
        String userLogin = "Login";
        String userPassword = "P4SSW0RD";
        String userMail = "mmail@7r.perm.ru";
        String csvLine = userLastName + ";" + userFirstName + ";"  + userMiddleName + ";" + userLocationName + ";" +
                         userDepartmentName + ";" + userPosition + ";" + userPc + ";" + userLogin + ";" + userPassword + ";" + userMail;

        InputStream inputStream = new ByteArrayInputStream(csvLine.getBytes());

        ImportCSVService.loadUsersFromCSV(addedOrganzation, inputStream, ';');

        List<User> userList = UserService.getUsersByDepartment(DepartmentService.getByName(userDepartmentName));
        User user = userList.get(0);

        try {
            Assert.assertEquals(userLastName, user.getLastName());
            Assert.assertEquals(userFirstName, user.getFirstName());
            Assert.assertEquals(userMiddleName, user.getMiddleName());
            Assert.assertEquals(userLocationName, user.getLocation().getName());
            Assert.assertEquals(userOrganizationName, user.getDepartment().getOrganization().getName());
            Assert.assertEquals(userDepartmentName, user.getDepartment().getName());
            Assert.assertEquals(userPosition, user.getPosition());
            Assert.assertEquals(userPc, user.getPc().getName());
            Assert.assertEquals(userLogin, user.getLogin());
            Assert.assertEquals(userPassword, user.getPassword());
            Assert.assertEquals(userMail, user.getMail());

        } finally {
           UserService.delete(user.getId());
           PcService.delete(user.getPc().getId());
           LocationService.delete(user.getLocation().getId());
           DepartmentService.delete(user.getDepartment().getId());
           OrganizationService.delete(user.getDepartment().getOrganization().getId());
        }

    }

}
