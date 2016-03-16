package crudDB;


import objects.Network;
import javax.persistence.TypedQuery;
import java.util.List;

import static crudDB.QueryProvider.doQueryCasual;
import static crudDB.QueryProvider.doQueryInTransaction;

public class NetworkService {


    public static Network add(Network network){
        return doQueryInTransaction(manager -> manager.merge(network));
    }

    public static void delete(long id){
        doQueryInTransaction(manager -> {
            manager.remove(manager.find(Network.class, id));
            return null;
        });
    }

    public static Network get(long id){
        return doQueryCasual(manager -> manager.find(Network.class, id));
    }

    public static void update(Network network){
        doQueryInTransaction(manager -> {
            manager.merge(network);
            return null;
        });
    }

    public static List<Network> getAll() {
        return doQueryCasual(manager -> {
            TypedQuery<Network> namedQuery = manager.createNamedQuery("Network.getAll", Network.class);
            return namedQuery.getResultList();
        });
    }

}
