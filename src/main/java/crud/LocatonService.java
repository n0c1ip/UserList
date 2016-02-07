package crud;

import objects.Location;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class LocatonService {

    public EntityManager manager = Persistence.createEntityManagerFactory("UserList").createEntityManager();

    public Location add(Location location){
        manager.getTransaction().begin();
        Location locationFromDB = manager.merge(location);
        manager.getTransaction().commit();
        return locationFromDB;
    }

    public void delete(long id){
        manager.getTransaction().begin();
        manager.remove(get(id));
        manager.getTransaction().commit();
    }

    public Location get(long id){
        return manager.find(Location.class, id);
    }

    public void update(Location location){
        manager.getTransaction().begin();
        manager.merge(location);
        manager.getTransaction().commit();
    }

    public Location getByName(String name) {
        TypedQuery<Location> namedQuery = manager.createNamedQuery("Location.getByName", Location.class);
        namedQuery.setParameter("name", name);
        return namedQuery.getSingleResult();
    }

    public List<Location> getAll(){
        TypedQuery<Location> namedQuery = manager.createNamedQuery("Location.getAll", Location.class);
        return namedQuery.getResultList();
    }

}
