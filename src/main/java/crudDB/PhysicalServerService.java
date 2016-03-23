package crudDB;

import objects.PhysicalServer;

import javax.persistence.TypedQuery;
import java.util.List;

import static crudDB.QueryProvider.doQueryCasual;
import static crudDB.QueryProvider.doQueryInTransaction;

public class PhysicalServerService {

    public static PhysicalServer add(PhysicalServer ps){
        return doQueryInTransaction(manager -> manager.merge(ps));
    }

    public static void delete(long id){
        doQueryInTransaction(manager -> {
            manager.remove(manager.find(PhysicalServer.class, id));
            return null;
        });
    }

    public static PhysicalServer get(long id){
        return doQueryCasual(manager -> manager.find(PhysicalServer.class, id));
    }

    public static void update(PhysicalServer ps){
        doQueryInTransaction(manager -> {
            manager.merge(ps);
            return null;
        });
    }

    public static PhysicalServer getByName(String name) {
        return doQueryCasual(manager -> {
            TypedQuery<PhysicalServer> namedQuery = manager.createNamedQuery("PhysicalServer.getByName", PhysicalServer.class);
            namedQuery.setParameter("name", name);
            return namedQuery.getSingleResult();
        });
    }

    public static List<PhysicalServer> getAll() {
        return doQueryCasual(manager -> {
            TypedQuery<PhysicalServer> namedQuery = manager.createNamedQuery("PhysicalServer.getAll", PhysicalServer.class);
            return namedQuery.getResultList();
        });
    }

}
