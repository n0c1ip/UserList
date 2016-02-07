package crud;

import objects.Location;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class LocationService {

    public static Location add(Location location){
        EntityManager manager = EntityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        Location locationFromDB = manager.merge(location);
        manager.getTransaction().commit();
        manager.close();
        return locationFromDB;
    }

    public static void delete(long id){
        EntityManager manager = EntityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        manager.remove(manager.find(Location.class, id)); //EntityManager.remove() works only on entities which are managed in the current transaction/context.
        manager.getTransaction().commit();
        manager.close();
    }

    public static Location get(long id){
        EntityManager manager = EntityManagerFactory.createEntityManager();
        Location foundLocation = manager.find(Location.class, id);
        manager.close();
        return foundLocation;
    }

    public static void update(Location location){
        EntityManager manager = EntityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        manager.merge(location);
        manager.getTransaction().commit();
        manager.close();
    }

    public static Location getByName(String name) {
        EntityManager manager = EntityManagerFactory.createEntityManager();
        TypedQuery<Location> namedQuery = manager.createNamedQuery("Location.getByName", Location.class);
        namedQuery.setParameter("name", name);
        Location foundLocation = namedQuery.getSingleResult();
        manager.close();
        return foundLocation;
    }

    public static List<Location> getAll(){
        EntityManager manager = EntityManagerFactory.createEntityManager();
        TypedQuery<Location> namedQuery = manager.createNamedQuery("Location.getAll", Location.class);
        List<Location> resultsList = namedQuery.getResultList();
        manager.close();
        return resultsList;
    }

}
