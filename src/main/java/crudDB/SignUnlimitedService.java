package crudDB;

import objects.SignUnlimited;

import javax.persistence.TypedQuery;
import java.util.List;

import static crudDB.QueryProvider.doQueryCasual;
import static crudDB.QueryProvider.doQueryInTransaction;

public class SignUnlimitedService {

    public static SignUnlimited add(SignUnlimited signUnlimited){
        return doQueryInTransaction(manager -> manager.merge(signUnlimited));
    }

    public static void delete(long id){
        doQueryInTransaction(manager -> {
            manager.remove(manager.find(SignUnlimited.class, id));
            return null;
        });
    }

    public static SignUnlimited get(long id){
        return doQueryCasual(manager -> manager.find(SignUnlimited.class, id));
    }

    public static void update(SignUnlimited signUnlimited){
        doQueryInTransaction(manager -> {
            manager.merge(signUnlimited);
            return null;
        });
    }

    public static SignUnlimited getByName(String name) {
        return doQueryCasual(manager -> {
            TypedQuery<SignUnlimited> namedQuery = manager.createNamedQuery("SignUnlimited.getByName", SignUnlimited.class);
            namedQuery.setParameter("name", name);
            return namedQuery.getSingleResult();
        });
    }

    public static List<SignUnlimited> getAll() {
        return doQueryCasual(manager -> {
            TypedQuery<SignUnlimited> namedQuery = manager.createNamedQuery("SignUnlimited.getAll", SignUnlimited.class);
            return namedQuery.getResultList();
        });
    }

}
