package crudDB;

import crudFiles.SettingsService;
import objects.Settings;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EntityManagerFactory {

    private static javax.persistence.EntityManagerFactory entityManagerFactory;

    static {
        Map<String, String> properties = new HashMap<>();
        Optional<Settings> optionalSettings = SettingsService.readSettings();

        if (optionalSettings.isPresent()) {
            Settings settings = optionalSettings.get();
            properties.put("hibernate.connection.username", settings.getUserName());
            properties.put("hibernate.connection.password", settings.getPassword());
            properties.put("hibernate.connection.url", settings.getServerWithPrefix());
            properties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        }
        entityManagerFactory = Persistence.createEntityManagerFactory("UserList", properties);
    }

    private EntityManagerFactory() {}

    public static void initialize() {}

    public static EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void closeEntityManagerFactory(){
        entityManagerFactory.close();
    }
}
