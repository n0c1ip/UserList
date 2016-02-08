package crud;

import objects.Location;

import javax.persistence.TypedQuery;
import java.util.List;

import static crud.QueryProvider.doQueryInTransaction;
import static crud.QueryProvider.doQueryCasual;

public class LocationService {

    public static Location add(Location location){
        return doQueryInTransaction(manager -> manager.merge(location));
    }

    public static void delete(long id){
        doQueryInTransaction(manager -> {
            manager.remove(manager.find(Location.class, id));
            return null;
        });
    }

    public static Location get(long id){
        return doQueryCasual(manager -> manager.find(Location.class, id));
    }

    public static void update(Location location){
        doQueryInTransaction(manager -> {
            manager.merge(location);
            return null;
        });
    }

    public static Location getByName(String name) {
        return doQueryCasual(manager -> {
            TypedQuery<Location> namedQuery = manager.createNamedQuery("Location.getByName", Location.class);
            namedQuery.setParameter("name", name);
            return namedQuery.getSingleResult();
        });
    }

    public static List<Location> getAll() {
        return doQueryCasual(manager -> {
            TypedQuery<Location> namedQuery = manager.createNamedQuery("Location.getAll", Location.class);
            return namedQuery.getResultList();
        });
    }

}
