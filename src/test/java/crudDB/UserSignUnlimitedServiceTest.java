package crudDB;

import objects.*;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class UserSignUnlimitedServiceTest {

    /**
     * Creates/deletes users for test purposes only
     */
    @Ignore
    private static class TestUsersManager {
        public static User createTestUser(String firstName) {
            Location location = new Location("LocationToTest");
            Location addedLocation = LocationService.add(location);
            Department department = new Department("DepartmentToTest");
            Department addedDepartment = DepartmentService.add(department);
            User user = new User();
            user.setFirstName(firstName);
            user.setLocation(addedLocation);
            user.setDepartment(addedDepartment);
            User addedUser = UserService.add(user);
            return addedUser;
        }

        public static void deleteTestUser(User user) {
            UserService.delete(user.getId());
            LocationService.delete(user.getLocation().getId());
            DepartmentService.delete(user.getDepartment().getId());
        }
    }

    @Test
    public void ShouldAddUserSignUnlimited() {
        UserSignUnlimited userSignUnlimited = new UserSignUnlimited("UserSignUnlimitedToAdd");

        UserSignUnlimited addedUserSignUnlimited = UserSignUnlimitedService.add(userSignUnlimited);
        UserSignUnlimited foundUserSignUnlimited = UserSignUnlimitedService.get(addedUserSignUnlimited.getId());

        try {
            Assert.assertNotNull(addedUserSignUnlimited);
            Assert.assertNotNull(foundUserSignUnlimited);
            Assert.assertEquals(addedUserSignUnlimited.getId(), foundUserSignUnlimited.getId());
        } finally {
            UserSignUnlimitedService.delete(addedUserSignUnlimited.getId());
        }
    }

    @Test
    public void ShouldDeleteUserSignUnlimited() {
        UserSignUnlimited userSignUnlimited = new UserSignUnlimited("UserSignUnlimitedToDelete");

        UserSignUnlimited addedUserSignUnlimited = UserSignUnlimitedService.add(userSignUnlimited);
        long addedUserSignUnlimitedId = addedUserSignUnlimited.getId();
        UserSignUnlimitedService.delete(addedUserSignUnlimitedId);

        Assert.assertNull(UserSignUnlimitedService.get(addedUserSignUnlimitedId));
    }

    @Test
    public void ShouldUpdateUserSignUnlimited() {
        String oldValue = "UserSignUnlimitedToUpdate";
        String newValue = "updatedUserSignUnlimited";
        UserSignUnlimited userSignUnlimited = new UserSignUnlimited();

        userSignUnlimited.setValue(oldValue);
        UserSignUnlimited addedUserSignUnlimited = UserSignUnlimitedService.add(userSignUnlimited);

        addedUserSignUnlimited.setValue(newValue);
        UserSignUnlimitedService.update(addedUserSignUnlimited);
        UserSignUnlimited foundUserSignUnlimited = UserSignUnlimitedService.get(addedUserSignUnlimited.getId());

        try {
            Assert.assertEquals(foundUserSignUnlimited.getValue(), newValue);
        } finally {
            UserSignUnlimitedService.delete(foundUserSignUnlimited.getId());
        }
    }


    @Test
    public void ShouldGetAllUserSignUnlimited() {
        int expectedUserSignUnlimitedCount = 3;
        String value1 = "UserSignUnlimitedOne";
        String value2 = "UserSignUnlimitedTwo";
        String value3 = "UserSignUnlimitedThree";

        List<String> valuesList = Arrays.asList(value1, value2, value3);

        UserSignUnlimited addedUserSignUnlimited1 = UserSignUnlimitedService.add(new UserSignUnlimited(value1));
        UserSignUnlimited addedUserSignUnlimited2 = UserSignUnlimitedService.add(new UserSignUnlimited(value2));
        UserSignUnlimited addedUserSignUnlimited3 = UserSignUnlimitedService.add(new UserSignUnlimited(value3));

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
            UserSignUnlimitedService.delete(addedUserSignUnlimited1.getId());
            UserSignUnlimitedService.delete(addedUserSignUnlimited2.getId());
            UserSignUnlimitedService.delete(addedUserSignUnlimited3.getId());
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
