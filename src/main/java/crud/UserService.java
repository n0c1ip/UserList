package crud;

import objects.Department;
import objects.Location;
import objects.User;

import javax.persistence.TypedQuery;
import java.util.List;

import static crud.QueryProvider.doQueryCasual;
import static crud.QueryProvider.doQueryInTransaction;

public class UserService {

    public static User add(User user){
        return doQueryInTransaction(manager -> manager.merge(user));
    }

    public static void delete(long id){
        doQueryInTransaction(manager -> {
            manager.remove(manager.find(User.class, id));
            return null;
        });
    }

    public static User get(long id){
        return doQueryCasual(manager -> manager.find(User.class, id));
    }

    public static void update(User user){
        doQueryInTransaction(manager -> {
            manager.merge(user);
            return null;
        });
    }

    public static List<User> getUsersByLocation(Location location){
        return doQueryCasual(manager -> {
            TypedQuery<User> getUserByLocation = manager.createNamedQuery("User.getUsersByLocation", User.class);
            getUserByLocation.setParameter("location", location);
            return getUserByLocation.getResultList();
        });
    }

    public static List<User> getUsersByDepartment(Department department){
        return doQueryCasual(manager -> {
            TypedQuery<User> usersByDepartment = manager.createNamedQuery("User.getUsersByDepartment", User.class);
            usersByDepartment.setParameter("department", department);
            return usersByDepartment.getResultList();
        });
    }

    public static List<User> getAll(){
        return doQueryCasual(manager -> {
            TypedQuery<User> namedQuery = manager.createNamedQuery("User.getAll", User.class);
            return namedQuery.getResultList();
        });
    }

}
