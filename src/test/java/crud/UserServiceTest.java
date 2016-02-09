package crud;


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
     * Creates users for test purposes only
     */
    @Ignore
    private static class TestUsersFactory {
        public static User getTestUser(String firstName) {
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
    }

    @Test
    public void ShouldAddUser() {
        User user = TestUsersFactory.getTestUser("UserToAdd");
        User addedUser = UserService.add(user);
        User foundUser = UserService.get(addedUser.getId());

        try {
            Assert.assertNotNull(addedUser);
            Assert.assertNotNull(foundUser);
            Assert.assertEquals(addedUser.getId(), foundUser.getId());
        } finally {
            UserService.delete(addedUser.getId());
            LocationService.delete(addedUser.getLocation().getId());
            DepartmentService.delete(addedUser.getDepartment().getId());
        }
    }

    @Test
    public void ShouldDeleteUser() {
        User user = TestUsersFactory.getTestUser("UserToDelete");

        User addedUser = UserService.add(user);
        long addedUserId = addedUser.getId();
        UserService.delete(addedUserId);

        try {
            Assert.assertNull(UserService.get(addedUserId));
        } finally {
            LocationService.delete(addedUser.getLocation().getId());
            DepartmentService.delete(addedUser.getDepartment().getId());
        }
    }

    @Test
    public void ShouldUpdateUser() {
        String oldName = "oldUserName";
        String newName = "newUserName";
        User user = TestUsersFactory.getTestUser(oldName);
        User addedUser = UserService.add(user);

        addedUser.setFirstName(newName);
        UserService.update(addedUser);
        User foundUser = UserService.get(addedUser.getId());

        try {
            Assert.assertEquals(foundUser.getFirstName(), newName);
        } finally {
            UserService.delete(foundUser.getId());
            LocationService.delete(foundUser.getLocation().getId());
            DepartmentService.delete(foundUser.getDepartment().getId());
        }
    }

    @Test
    public void ShouldGetUsersByLocationName() {
        User user = TestUsersFactory.getTestUser("UserToGetByLocationName");
        User addedUser = UserService.add(user);

        boolean UserIsFound = false;
        List<User> UserList = UserService.getUsersByLocationName(addedUser.getLocation().getName());
        for (User foundUser : UserList) {
            if (foundUser.getId() == addedUser.getId()) {
                UserIsFound = true;
            }
        }

        try {
            Assert.assertTrue(UserIsFound);
        } finally {
            UserService.delete(addedUser.getId());
            LocationService.delete(addedUser.getLocation().getId());
            DepartmentService.delete(addedUser.getDepartment().getId());
        }
    }

    @Test
    public void ShouldGetUsersByDepartment() {
        User user = TestUsersFactory.getTestUser("UserToGetByDepartment");
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
            UserService.delete(addedUser.getId());
            LocationService.delete(addedUser.getLocation().getId());
            DepartmentService.delete(addedUser.getDepartment().getId());
        }
    }

    @Test
    public void ShouldGetAllUsers() {
        int expectedUsersCount = 2;
        User user1 = TestUsersFactory.getTestUser("User1ToGetAll");
        User user2 = TestUsersFactory.getTestUser("User2ToGetAll");

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
            UserService.delete(addedUser1.getId());
            LocationService.delete(addedUser1.getLocation().getId());
            DepartmentService.delete(addedUser1.getDepartment().getId());
            UserService.delete(addedUser2.getId());
            LocationService.delete(addedUser2.getLocation().getId());
            DepartmentService.delete(addedUser2.getDepartment().getId());
        }
    }
}
