package crud;

import objects.Department;
import objects.Location;
import objects.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserService {

    public static User add(User user){
            EntityManager manager = EntityManagerFactory.createEntityManager();
            manager.getTransaction().begin();
            User userFromDB = manager.merge(user);
            manager.getTransaction().commit();
            manager.close();
            return userFromDB;
        }

        public static void delete(long id){
            EntityManager manager = EntityManagerFactory.createEntityManager();
            manager.getTransaction().begin();
            manager.remove(manager.find(User.class, id));
            manager.getTransaction().commit();
            manager.close();
        }

        public static User get(long id){
            EntityManager manager = EntityManagerFactory.createEntityManager();
            User foundUser = manager.find(User.class, id);
            manager.close();
            return foundUser;
        }

        public static void update(User user){
            EntityManager manager = EntityManagerFactory.createEntityManager();
            manager.getTransaction().begin();
            manager.merge(user);
            manager.getTransaction().commit();
            manager.close();
        }

        public static List<User> getAll(){
            EntityManager manager = EntityManagerFactory.createEntityManager();
            TypedQuery<User> namedQuery = manager.createNamedQuery("User.getAll", User.class);
            return namedQuery.getResultList();
        }

        public static List<User> getUsersByLocationName(Location location){
            EntityManager manager = EntityManagerFactory.createEntityManager();
            TypedQuery<User> getUserByLocation = manager.createNamedQuery("User.getUsersByLocation", User.class);
            getUserByLocation.setParameter("location",location);
            return getUserByLocation.getResultList();
        }

        public static List<User> getUsersByDepartment(Department department){
            EntityManager manager = EntityManagerFactory.createEntityManager();
            TypedQuery<User> getUsersByDepartment = manager.createNamedQuery("User.getUsersByDepartment", User.class);
            getUsersByDepartment.setParameter("department", department);
            return getUsersByDepartment.getResultList();

        }

}
