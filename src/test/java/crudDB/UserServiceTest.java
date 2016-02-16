package crudDB;


import objects.Department;
import objects.Location;
import objects.User;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;


public class UserServiceTest {

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
            return user;
        }

        public static void deleteTestUser(User user) {
            UserService.delete(user.getId());
            LocationService.delete(user.getLocation().getId());
            DepartmentService.delete(user.getDepartment().getId());
        }
    }

    @Test
    public void ShouldAddUser() {
        User user = TestUsersManager.createTestUser("UserToAdd");
        User addedUser = UserService.add(user);
        User foundUser = UserService.get(addedUser.getId());

        try {
            Assert.assertNotNull(addedUser);
            Assert.assertNotNull(foundUser);
            Assert.assertEquals(addedUser.getId(), foundUser.getId());
        } finally {
            TestUsersManager.deleteTestUser(addedUser);
        }
    }

    @Test
    public void ShouldDeleteUser() {
        User user = TestUsersManager.createTestUser("UserToDelete");

        User addedUser = UserService.add(user);
        long addedUserId = addedUser.getId();
        TestUsersManager.deleteTestUser(addedUser);

        Assert.assertNull(UserService.get(addedUserId));
    }

    @Test
    public void ShouldUpdateUser() {
        String oldName = "oldUserName";
        String newName = "newUserName";
        User user = TestUsersManager.createTestUser(oldName);
        User addedUser = UserService.add(user);

        addedUser.setFirstName(newName);
        UserService.update(addedUser);
        User foundUser = UserService.get(addedUser.getId());

        try {
            Assert.assertEquals(foundUser.getFirstName(), newName);
        } finally {
            TestUsersManager.deleteTestUser(foundUser);
        }
    }

    @Test
    public void ShouldGetUsersByLocation() {
        User user = TestUsersManager.createTestUser("UserToGetByLocation");
        User addedUser = UserService.add(user);

        boolean UserIsFound = false;
        List<User> UserList = UserService.getUsersByLocation(addedUser.getLocation());
        for (User foundUser : UserList) {
            if (foundUser.getId() == addedUser.getId()) {
                UserIsFound = true;
            }
        }

        try {
            Assert.assertTrue(UserIsFound);
        } finally {
            TestUsersManager.deleteTestUser(addedUser);
        }
    }

    @Test
    public void ShouldGetUsersByDepartment() {
        User user = TestUsersManager.createTestUser("UserToGetByDepartment");
        User addedUser = UserService.add(user);

        boolean UserIsFound = false;
        List<User> UserList = UserService.getUsersByDepartment(addedUser.getDepartment());
        for (User foundUser : UserList) {
            if (foundUser.getId() == addedUser.getId()) {
                UserIsFound = true;
            }
        }

        try {
            Assert.assertTrue(UserIsFound);
        } finally {
            TestUsersManager.deleteTestUser(addedUser);
        }
    }

    @Test
    public void ShouldGetAllUsers() {
        int expectedUsersCount = 2;
        User user1 = TestUsersManager.createTestUser("User1ToGetAll");
        User user2 = TestUsersManager.createTestUser("User2ToGetAll");

        User addedUser1 = UserService.add(user1);
        User addedUser2 = UserService.add(user2);

        List<Long> usersIdList = Arrays.asList(addedUser1.getId(), addedUser2.getId());

        int usersFound = 0;
        List<User> UserList = UserService.getAll();
        for (User foundUser : UserList) {
            if (usersIdList.contains(foundUser.getId())) {
                usersFound++;
            }
        }

        try {
            Assert.assertEquals(expectedUsersCount, usersFound);
        } finally {
            TestUsersManager.deleteTestUser(addedUser1);
            TestUsersManager.deleteTestUser(addedUser2);
        }
    }
}
