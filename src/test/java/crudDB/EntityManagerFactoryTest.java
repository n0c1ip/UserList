package crudDB;

import objects.Settings;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Optional;

public class EntityManagerFactoryTest {

    @Test
    public void ShouldCreateEntityManager() {
        EntityManager entityManager = EntityManagerFactory.createEntityManager();

        Assert.assertNotNull(entityManager);
        Assert.assertTrue(entityManager.isOpen());
    }

    @Test
    public void ShouldGetActiveSettings() {
        Optional<Settings> optionalSettings = EntityManagerFactory.getActiveSettings();
        if (optionalSettings.isPresent()) {
            Settings settings = optionalSettings.get();
            Assert.assertNotNull(settings.getServer());
            Assert.assertNotNull(settings.getUserName());
            Assert.assertNotNull(settings.getPassword());
        }
    }

}