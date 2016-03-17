package crudDB;

import objects.Vlan;
import javax.persistence.TypedQuery;
import java.util.List;

import static crudDB.QueryProvider.doQueryCasual;
import static crudDB.QueryProvider.doQueryInTransaction;

public class VlanService {

    public static Vlan add(Vlan vlan){
        return doQueryInTransaction(manager -> manager.merge(vlan));
    }

    public static void delete(long id){
        doQueryInTransaction(manager -> {
            manager.remove(manager.find(Vlan.class, id));
            return null;
        });
    }

    public static Vlan get(long id){
        return doQueryCasual(manager -> manager.find(Vlan.class, id));
    }

    public static void update(Vlan vlan){
        doQueryInTransaction(manager -> {
            manager.merge(vlan);
            return null;
        });
    }

    public static List<Vlan> getAll() {
        return doQueryCasual(manager -> {
            TypedQuery<Vlan> namedQuery = manager.createNamedQuery("Vlan.getAll", Vlan.class);
            return namedQuery.getResultList();
        });
    }


}
