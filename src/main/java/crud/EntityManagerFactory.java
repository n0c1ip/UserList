package crud;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerFactory {

    private static javax.persistence.EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserList");

    private EntityManagerFactory() {}

    public static EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
