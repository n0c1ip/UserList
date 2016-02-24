package crudDB;

import objects.SignUnlimited;

import javax.persistence.TypedQuery;
import java.util.List;

import static crudDB.QueryProvider.doQueryCasual;
import static crudDB.QueryProvider.doQueryInTransaction;

public class SignUnlimitedService {

    public static SignUnlimited add(SignUnlimited SignUnlimited){
        return doQueryInTransaction(manager -> manager.merge(SignUnlimited));
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

    public static void update(SignUnlimited SignUnlimited){
        doQueryInTransaction(manager -> {
            manager.merge(SignUnlimited);
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
