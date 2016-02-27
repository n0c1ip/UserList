package crudDB;

import objects.Classification;
import javax.persistence.TypedQuery;
import java.util.List;
import static crudDB.QueryProvider.doQueryCasual;
import static crudDB.QueryProvider.doQueryInTransaction;

public class ClassificationService {

    public static Classification add(Classification classification){
        return doQueryInTransaction(manager -> manager.merge(classification));
    }

    public static void delete(long id){
        doQueryInTransaction(manager -> {
            manager.remove(manager.find(Classification.class, id));
            return null;
        });
    }

    public static Classification get(long id){
        return doQueryCasual(manager -> manager.find(Classification.class, id));
    }

    public static void update(Classification classification){
        doQueryInTransaction(manager -> {
            manager.merge(classification);
            return null;
        });
    }

    public static Classification getByName(String name) {
        return doQueryCasual(manager -> {
            TypedQuery<Classification> namedQuery = manager.createNamedQuery("Classification.getByName", Classification.class);
            namedQuery.setParameter("name", name);
            return namedQuery.getSingleResult();
        });
    }

    public static List<Classification> getAll() {
        return doQueryCasual(manager -> {
            TypedQuery<Classification> namedQuery = manager.createNamedQuery("Classification.getAll", Classification.class);
            return namedQuery.getResultList();
        });
    }

}
