package crudDB;

import objects.*;
import org.junit.*;

import java.util.Arrays;
import java.util.List;

public class UserSignUnlimitedServiceTest {

    private static Organization organization = null;

    @BeforeClass
    public static void createOrganization() {
        Organization org = new Organization("TestOrgQueryProvider");
        organization = OrganizationService.add(org);
    }

    @AfterClass
    public static void deleteOrganization() {
        OrganizationService.delete(organization.getId());
    }

    /**
     * Creates/deletes users for test purposes only
     */
    @Ignore
    private static class TestUsersManager {
        public static User createTestUser(String firstName) {
            Location location = new Location("LocationToTest");
            Location addedLocation = LocationService.add(location);
            Department department = new Department("DepartmentToTest", organization);
            Department addedDepartment = DepartmentService.add(department);
            User user = new User();
            user.setFirstName(firstName);
            user.setLocation(addedLocation);
            user.setDepartment(addedDepartment);
            user.setLastName("vasian");
            user.setPosition("megaleader");
            user.setMail("mail@mail.mail");
            user.setLogin("login");
            user.setPassword("topsecret");
            return UserService.add(user);
        }

        public static void deleteTestUser(User user) {
            UserService.delete(user.getId());
            LocationService.delete(user.getLocation().getId());
            DepartmentService.delete(user.getDepartment().getId());
        }
    }

    /**
     * Creates/deletes user signs for test purposes only
     */
    @Ignore
    private static class UserSignUnlimitedManager {
        public static UserSignUnlimited createUserSignUnlimited(String value) {
            UserSignUnlimited userSignUnlimited = new UserSignUnlimited(value);
            SignUnlimited signUnlimited = SignUnlimitedService.add(new SignUnlimited("SignUnlimitedToTestByUser"));
            userSignUnlimited.setSignUnlimited(signUnlimited);
            User user = TestUsersManager.createTestUser("UserToAddByUser");
            userSignUnlimited.setUser(user);
            return UserSignUnlimitedService.add(userSignUnlimited);
        }

        public static void deleteUserSignUnlimited(UserSignUnlimited userSignUnlimited) {
            UserSignUnlimitedService.delete(userSignUnlimited.getId());
            SignUnlimitedService.delete(userSignUnlimited.getSignUnlimited().getId());
            TestUsersManager.deleteTestUser(userSignUnlimited.getUser());
        }
    }

    @Test
    public void ShouldAddUserSignUnlimited() {
        UserSignUnlimited addedUserSignUnlimited = UserSignUnlimitedManager.createUserSignUnlimited("UserSignUnlimitedToAdd");
        UserSignUnlimited foundUserSignUnlimited = UserSignUnlimitedService.get(addedUserSignUnlimited.getId());

        try {
            Assert.assertNotNull(addedUserSignUnlimited);
            Assert.assertNotNull(foundUserSignUnlimited);
            Assert.assertEquals(addedUserSignUnlimited.getId(), foundUserSignUnlimited.getId());
        } finally {
            UserSignUnlimitedManager.deleteUserSignUnlimited(addedUserSignUnlimited);
        }
    }

    @Test
    public void ShouldDeleteUserSignUnlimited() {
        UserSignUnlimited addedUserSignUnlimited = UserSignUnlimitedManager.createUserSignUnlimited("UserSignUnlimitedToDelete");

        long addedUserSignUnlimitedId = addedUserSignUnlimited.getId();
        UserSignUnlimitedManager.deleteUserSignUnlimited(addedUserSignUnlimited);

        Assert.assertNull(UserSignUnlimitedService.get(addedUserSignUnlimitedId));

    }

    @Test
    public void ShouldUpdateUserSignUnlimited() {
        String oldValue = "UserSignUnlimitedToUpdate";
        String newValue = "updatedUserSignUnlimited";

        UserSignUnlimited addedUserSignUnlimited = UserSignUnlimitedManager.createUserSignUnlimited(oldValue);

        addedUserSignUnlimited.setValue(newValue);
        UserSignUnlimitedService.update(addedUserSignUnlimited);
        UserSignUnlimited foundUserSignUnlimited = UserSignUnlimitedService.get(addedUserSignUnlimited.getId());

        try {
            Assert.assertEquals(foundUserSignUnlimited.getValue(), newValue);
        } finally {
            UserSignUnlimitedManager.deleteUserSignUnlimited(addedUserSignUnlimited);
        }
    }


    @Test
    public void ShouldGetAllUserSignUnlimited() {
        int expectedUserSignUnlimitedCount = 2;
        String value1 = "UserSignUnlimitedOne";
        String value2 = "UserSignUnlimitedTwo";

        List<String> valuesList = Arrays.asList(value1, value2);
        UserSignUnlimited addedUserSignUnlimited1 = UserSignUnlimitedManager.createUserSignUnlimited(value1);
        UserSignUnlimited addedUserSignUnlimited2 = UserSignUnlimitedManager.createUserSignUnlimited(value2);

        int UserSignUnlimitedFound = 0;
        List<UserSignUnlimited> UserSignUnlimitedList = UserSignUnlimitedService.getAll();
        for (UserSignUnlimited UserSignUnlimited : UserSignUnlimitedList) {
            if (valuesList.contains(UserSignUnlimited.getValue())) {
                UserSignUnlimitedFound++;
            }
        }

        try {
            Assert.assertEquals(expectedUserSignUnlimitedCount, UserSignUnlimitedFound);
        } finally {
            UserSignUnlimitedManager.deleteUserSignUnlimited(addedUserSignUnlimited1);
            UserSignUnlimitedManager.deleteUserSignUnlimited(addedUserSignUnlimited2);
        }
    }

    @Test
    public void ShouldGetUserSignUnlimitedByUser() {
        UserSignUnlimited userSignUnlimited = new UserSignUnlimited("UserSignUnlimitedToGetByUser");

        User user = TestUsersManager.createTestUser("UserToAddByUser");
        SignUnlimited signUnlimited = SignUnlimitedService.add(new SignUnlimited("SignUnlimitedToTestByUser"));

        userSignUnlimited.setUser(user);
        userSignUnlimited.setSignUnlimited(signUnlimited);

        UserSignUnlimited addedUserSignUnlimited = UserSignUnlimitedService.add(userSignUnlimited);
        List <UserSignUnlimited> gottenUserSignUnlimitedList = UserSignUnlimitedService.getByUser(user);
        UserSignUnlimited gottenUserSignUnlimited = gottenUserSignUnlimitedList.get(0);

        try {
            Assert.assertNotNull(gottenUserSignUnlimited);
            Assert.assertEquals(userSignUnlimited.getValue(), gottenUserSignUnlimited.getValue());
            Assert.assertEquals(userSignUnlimited.getUser().getId(), gottenUserSignUnlimited.getUser().getId());
            Assert.assertEquals(userSignUnlimited.getSignUnlimited().getId(), gottenUserSignUnlimited.getSignUnlimited().getId());

        } finally {
            UserSignUnlimitedService.delete(addedUserSignUnlimited.getId());
            TestUsersManager.deleteTestUser(user);
            SignUnlimitedService.delete(signUnlimited.getId());
        }
    }

    @Test
    public void ShouldGetUserSignUnlimitedBySign() {
        UserSignUnlimited userSignUnlimited = new UserSignUnlimited("UserSignUnlimitedToGetBySign");

        User user = TestUsersManager.createTestUser("UserToAddBySign");
        SignUnlimited signUnlimited = SignUnlimitedService.add(new SignUnlimited("SignUnlimitedToTestBySign"));

        userSignUnlimited.setUser(user);
        userSignUnlimited.setSignUnlimited(signUnlimited);

        UserSignUnlimited addedUserSignUnlimited = UserSignUnlimitedService.add(userSignUnlimited);
        List <UserSignUnlimited> gottenUserSignUnlimitedList = UserSignUnlimitedService.getBySignUnlimited(signUnlimited);
        UserSignUnlimited gottenUserSignUnlimited = gottenUserSignUnlimitedList.get(0);

        try {
            Assert.assertNotNull(gottenUserSignUnlimited);
            Assert.assertEquals(userSignUnlimited.getValue(), gottenUserSignUnlimited.getValue());
            Assert.assertEquals(userSignUnlimited.getUser().getId(), gottenUserSignUnlimited.getUser().getId());
            Assert.assertEquals(userSignUnlimited.getSignUnlimited().getId(), gottenUserSignUnlimited.getSignUnlimited().getId());

        } finally {
            UserSignUnlimitedService.delete(addedUserSignUnlimited.getId());
            TestUsersManager.deleteTestUser(user);
            SignUnlimitedService.delete(signUnlimited.getId());
        }
    }
}
