package crudDB;

import javax.persistence.EntityManager;

public class QueryProvider {

    public interface Query<T> {
        T call(EntityManager manager);
    }

    static <T> T doQueryInTransaction(Query<T> callable){
        EntityManager manager = EntityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        T returnValue = callable.call(manager);
        manager.getTransaction().commit();
        manager.close();
        return returnValue;
    }

    static <T> T doQueryCasual(Query<T> callable){
        EntityManager manager = EntityManagerFactory.createEntityManager();
        T returnValue = callable.call(manager);
        manager.close();
        return returnValue;
    }

}
