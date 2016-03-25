package crudDB;

import objects.NetworkEquipment;

import javax.persistence.TypedQuery;
import java.util.List;

import static crudDB.QueryProvider.doQueryCasual;
import static crudDB.QueryProvider.doQueryInTransaction;

public class NetworkEquipmentService {

    public static NetworkEquipment add(NetworkEquipment networkEquipment){
        return doQueryInTransaction(manager -> manager.merge(networkEquipment));
    }

    public static void delete(long id){
        doQueryInTransaction(manager -> {
            manager.remove(manager.find(NetworkEquipment.class, id));
            return null;
        });
    }

    public static NetworkEquipment get(long id){
        return doQueryCasual(manager -> manager.find(NetworkEquipment.class, id));
    }

    public static void update(NetworkEquipment networkEquipment){
        doQueryInTransaction(manager -> {
            manager.merge(networkEquipment);
            return null;
        });
    }

    public static List<NetworkEquipment> getAll() {
        return doQueryCasual(manager -> {
            TypedQuery<NetworkEquipment> namedQuery =
                    manager.createNamedQuery("NetworkEquipment.getAll", NetworkEquipment.class);
            return namedQuery.getResultList();
        });
    }
}
