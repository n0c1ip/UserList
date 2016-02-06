package crud;

import objects.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserService {

        public EntityManager manager = Persistence.createEntityManagerFactory("UserList").createEntityManager();

        public User add(User user){
            manager.getTransaction().begin();
            User userFromDB = manager.merge(user);
            manager.getTransaction().commit();
            return userFromDB;
        }

        public void delete(long id){
            manager.getTransaction().begin();
            manager.remove(get(id));
            manager.getTransaction().commit();
        }

        public User get(long id){
            return manager.find(User.class, id);
        }

        public void update(User user){
            manager.getTransaction().begin();
            manager.merge(user);
            manager.getTransaction().commit();
        }

        public List<User> getAll(){
            TypedQuery<User> namedQuery = manager.createNamedQuery("User.getAll", User.class);
            return namedQuery.getResultList();
        }

}
