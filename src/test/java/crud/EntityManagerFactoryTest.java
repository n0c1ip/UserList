package crud;

import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;

public class EntityManagerFactoryTest {

    @Test
    public void ShouldCreateEntityManager() {
        EntityManager entityManager = EntityManagerFactory.createEntityManager();

        Assert.assertNotNull(entityManager);
        Assert.assertTrue(entityManager.isOpen());
    }

}