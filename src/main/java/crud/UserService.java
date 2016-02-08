package crud;

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

    public static List<User> getUsersByLocationName(String locationName){
        return doQueryCasual(manager -> {
            TypedQuery<Location> getLocationByName = manager.createNamedQuery("Location.getByName", Location.class);
            getLocationByName.setParameter("name", locationName);
            TypedQuery<User> getUserByLocatonName = manager.createNamedQuery("User.getUsersByLocationName", User.class);
            getUserByLocatonName.setParameter("location", getLocationByName.getSingleResult());
            return getUserByLocatonName.getResultList();
        });
    }

    public static List<User> getAll(){
        return doQueryCasual(manager -> {
            TypedQuery<User> namedQuery = manager.createNamedQuery("User.getAll", User.class);
            return namedQuery.getResultList();
        });
    }

}
