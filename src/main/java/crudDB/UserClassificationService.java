package crudDB;

import objects.User;
import objects.UserClassification;

import javax.persistence.TypedQuery;
import java.util.List;

import static crudDB.QueryProvider.doQueryCasual;
import static crudDB.QueryProvider.doQueryInTransaction;

public class UserClassificationService {

    public static UserClassification add(UserClassification userClassification){
        return doQueryInTransaction(manager -> manager.merge(userClassification));
    }

    public static void delete(long id){
        doQueryInTransaction(manager -> {
            manager.remove(manager.find(UserClassification.class, id));
            return null;
        });
    }

    public static UserClassification get(long id){
        return doQueryCasual(manager -> manager.find(UserClassification.class, id));
    }

    public static void update(UserClassification userClassification){
        doQueryInTransaction(manager -> {
            manager.merge(userClassification);
            return null;
        });
    }

    public static List<UserClassification> getAll() {
        return doQueryCasual(manager -> {
            TypedQuery<UserClassification> namedQuery = manager.createNamedQuery("UserClassification.getAll", UserClassification.class);
            return namedQuery.getResultList();
        });
    }

    public static List<UserClassification> getByUser(User user) {
        return doQueryCasual(manager -> {
            TypedQuery<UserClassification> namedQuery = manager.createNamedQuery("UserClassification.getByUser", UserClassification.class);
            namedQuery.setParameter("user", user);
            return namedQuery.getResultList();
        });
    }

}
