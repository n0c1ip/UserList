package crudDB;

import objects.*;
import org.junit.*;

import java.util.Arrays;
import java.util.List;


public class UserClassificationServiceTest {

    private static Organization organization;
    private static Location location;
    private static Department department;
    private static User user;
    private static Classification classification;

    @BeforeClass
    public static void createTestClassification() {
        Classification classific = new Classification("classificationToAdd");
        classification = ClassificationService.add(classific);
        Organization org = new Organization("TestUserClassificationService");
        organization = OrganizationService.add(org);
        Location loc = new Location("LocationToTest");
        location = LocationService.add(loc);
        Department dep = new Department("DepartmentToTest", organization);
        department = DepartmentService.add(dep);
        User usr = new User();
        usr.setFirstName("FirstName");
        usr.setLocation(location);
        usr.setDepartment(department);
        usr.setLastName("vasian");
        usr.setPosition("megaleader");
        usr.setMail("mail@mail.mail");
        usr.setLogin("login");
        usr.setPassword("topsecret");
        user = UserService.add(usr);
    }

    @AfterClass
    public static void deleteTestClassification() {
        UserService.delete(user.getId());
        LocationService.delete(location.getId());
        DepartmentService.delete(department.getId());
        OrganizationService.delete(organization.getId());
        ClassificationService.delete(classification.getId());
    }


    @Test
    public void ShouldAddUserClassification() {
        UserClassification userClassification = new UserClassification(user, classification);

        UserClassification addedUserClassification = UserClassificationService.add(userClassification);
        UserClassification foundUserClassification = UserClassificationService.get(addedUserClassification.getId());

        try {
            Assert.assertNotNull(addedUserClassification);
            Assert.assertNotNull(foundUserClassification);
            Assert.assertEquals(addedUserClassification.getId(), foundUserClassification.getId());
        } finally {
            UserClassificationService.delete(addedUserClassification.getId());
        }
    }

    @Test
    public void ShouldDeleteUserClassification() {
        UserClassification userClassification = new UserClassification(user, classification);

        UserClassification addedUserClassification = UserClassificationService.add(userClassification);
        long addedUserClassificationId = addedUserClassification.getId();
        UserClassificationService.delete(addedUserClassificationId);

        Assert.assertNull(UserClassificationService.get(addedUserClassificationId));
    }


    @Test
    public void ShouldUpdateUserClassification() {
        UserClassification userClassification = new UserClassification(user, classification);
        UserClassification addedUserClassification = UserClassificationService.add(userClassification);

        Classification newClassification = new Classification("newClassification");
        newClassification = ClassificationService.add(newClassification);

        addedUserClassification.setClassification(newClassification);
        UserClassificationService.update(addedUserClassification);
        UserClassification foundUserClassification = UserClassificationService.get(addedUserClassification.getId());

        try {
            Assert.assertEquals(foundUserClassification.getClassification().getId(), newClassification.getId());
        } finally {
            UserClassificationService.delete(foundUserClassification.getId());
            ClassificationService.delete(newClassification.getId());
        }
    }


    @Test
    public void ShouldGetAllUserClassifications() {
        int expectedUserClassificationsCount = 2;

        Classification newClassification = new Classification("newClassification");
        newClassification = ClassificationService.add(newClassification);

        UserClassification addedUserClassification1 = UserClassificationService.add(new UserClassification(user, classification));
        UserClassification addedUserClassification2 = UserClassificationService.add(new UserClassification(user, newClassification));

        List<Long> classificationIdList = Arrays.asList(addedUserClassification1.getId(), addedUserClassification2.getId());

        int userClassificationsFound = 0;
        List<UserClassification> userClassificationList = UserClassificationService.getAll();
        for (UserClassification userClassification : userClassificationList) {
            if (classificationIdList.contains(userClassification.getId())) {
                userClassificationsFound++;
            }
        }

        try {
            Assert.assertEquals(expectedUserClassificationsCount, userClassificationsFound);
        } finally {
            UserClassificationService.delete(addedUserClassification1.getId());
            UserClassificationService.delete(addedUserClassification2.getId());
            ClassificationService.delete(newClassification.getId());
        }
    }

    @Test
    public void ShouldGetByUser() {
        UserClassification userClassification = new UserClassification(user, classification);

        UserClassification addedUserClassification = UserClassificationService.add(userClassification);
        List<UserClassification> foundUserClassification = UserClassificationService.getByUser(user);

        try {
            Assert.assertNotNull(addedUserClassification);
            Assert.assertNotNull(foundUserClassification);
            Assert.assertEquals(addedUserClassification.getId(), foundUserClassification.get(0).getId());
        } finally {
            UserClassificationService.delete(addedUserClassification.getId());
        }
    }

    @Test
    public void ShouldGetByUserAndClassification() {
        UserClassification userClassification = new UserClassification(user, classification);

        UserClassification addedUserClassification = UserClassificationService.add(userClassification);
        UserClassification foundUserClassification = UserClassificationService.getByUserAndClassification(user, classification);

        try {
            Assert.assertNotNull(addedUserClassification);
            Assert.assertNotNull(foundUserClassification);
            Assert.assertEquals(addedUserClassification.getId(), foundUserClassification.getId());
        } finally {
            UserClassificationService.delete(addedUserClassification.getId());
        }
    }
}