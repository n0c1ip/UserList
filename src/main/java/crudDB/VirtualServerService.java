package crudDB;

import objects.PhysicalServer;
import objects.VirtualServer;

import javax.persistence.TypedQuery;
import java.util.List;

import static crudDB.QueryProvider.doQueryCasual;
import static crudDB.QueryProvider.doQueryInTransaction;

public class VirtualServerService {

    public static VirtualServer add(VirtualServer vs){
        return doQueryInTransaction(manager -> manager.merge(vs));
    }

    public static void delete(long id){
        doQueryInTransaction(manager -> {
            manager.remove(manager.find(VirtualServer.class, id));
            return null;
        });
    }

    public static VirtualServer get(long id){
        return doQueryCasual(manager -> manager.find(VirtualServer.class, id));
    }

    public static void update(VirtualServer vs){
        doQueryInTransaction(manager -> {
            manager.merge(vs);
            return null;
        });
    }

    public static VirtualServer getByName(String name) {
        return doQueryCasual(manager -> {
            TypedQuery<VirtualServer> namedQuery = manager.createNamedQuery("VirtualServer.getByName", VirtualServer.class);
            namedQuery.setParameter("name", name);
            return namedQuery.getSingleResult();
        });
    }

    public static List<VirtualServer> getByPServer(PhysicalServer pServer) {
        return doQueryCasual(manager -> {
            TypedQuery<VirtualServer> namedQuery = manager.createNamedQuery("VirtualServer.getByPServer", VirtualServer.class);
            namedQuery.setParameter("pServer", pServer);
            return namedQuery.getResultList();
        });
    }

    public static List<VirtualServer> getAll() {
        return doQueryCasual(manager -> {
            TypedQuery<VirtualServer> namedQuery = manager.createNamedQuery("VirtualServer.getAll", VirtualServer.class);
            return namedQuery.getResultList();
        });
    }

}
