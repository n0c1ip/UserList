package crudDB;

import objects.Pc;

import javax.persistence.TypedQuery;
import java.util.List;

import static crudDB.QueryProvider.doQueryCasual;
import static crudDB.QueryProvider.doQueryInTransaction;

public class PcService {

    public static Pc add(Pc pc){
        return doQueryInTransaction(manager -> manager.merge(pc));
    }

    public static void delete(long id){
        doQueryInTransaction(manager -> {
            manager.remove(manager.find(Pc.class, id));
            return null;
        });
    }

    public static Pc get(long id){
        return doQueryCasual(manager -> manager.find(Pc.class, id));
    }

    public static void update(Pc pc){
        doQueryInTransaction(manager -> {
            manager.merge(pc);
            return null;
        });
    }

    public static Pc getByName(String name) {
        return doQueryCasual(manager -> {
            TypedQuery<Pc> namedQuery = manager.createNamedQuery("Pc.getByName", Pc.class);
            namedQuery.setParameter("name", name);
            return namedQuery.getSingleResult();
        });
    }

    public static List<Pc> getAll() {
        return doQueryCasual(manager -> {
            TypedQuery<Pc> namedQuery = manager.createNamedQuery("Pc.getAll", Pc.class);
            return namedQuery.getResultList();
        });
    }

}
