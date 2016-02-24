package crudDB;

import objects.SignUnlimited;
import objects.User;
import objects.UserSignUnlimited;

import javax.persistence.TypedQuery;
import java.util.List;

import static crudDB.QueryProvider.doQueryCasual;
import static crudDB.QueryProvider.doQueryInTransaction;

public class UserSignUnlimitedService {

    public static UserSignUnlimited add(UserSignUnlimited userSignUnlimited){
        return doQueryInTransaction(manager -> manager.merge(userSignUnlimited));
    }

    public static void delete(long id){
        doQueryInTransaction(manager -> {
            manager.remove(manager.find(UserSignUnlimited.class, id));
            return null;
        });
    }

    public static UserSignUnlimited get(long id){
        return doQueryCasual(manager -> manager.find(UserSignUnlimited.class, id));
    }

    public static void update(UserSignUnlimited userSignUnlimited){
        doQueryInTransaction(manager -> {
            manager.merge(userSignUnlimited);
            return null;
        });
    }

    public static List<UserSignUnlimited> getAll() {
        return doQueryCasual(manager -> {
            TypedQuery<UserSignUnlimited> namedQuery = manager.createNamedQuery("UserSignUnlimited.getAll", UserSignUnlimited.class);
            return namedQuery.getResultList();
        });
    }

    public static List<UserSignUnlimited> getByUser(User user) {
        return doQueryCasual(manager -> {
            TypedQuery<UserSignUnlimited> namedQuery = manager.createNamedQuery("UserSignUnlimited.getByUser", UserSignUnlimited.class);
            namedQuery.setParameter("user", user);
            return namedQuery.getResultList();
        });
    }

    public static List<UserSignUnlimited> getBySignUnlimited(SignUnlimited signUnlimited) {
        return doQueryCasual(manager -> {
            TypedQuery<UserSignUnlimited> namedQuery = manager.createNamedQuery("UserSignUnlimited.getBySignUnlimited", UserSignUnlimited.class);
            namedQuery.setParameter("signUnlimited", signUnlimited);
            return namedQuery.getResultList();
        });
    }


}
