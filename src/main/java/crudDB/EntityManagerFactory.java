package crudDB;

import crudFiles.SettingsService;
import objects.Settings;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EntityManagerFactory {

    private EntityManagerFactory() {}

    private static javax.persistence.EntityManagerFactory entityManagerFactory;
    private static Settings settings;

    static {
        Map<String, String> properties = new HashMap<>();
        Optional<Settings> optionalSettings = SettingsService.readSettings();

        if (optionalSettings.isPresent()) {
            settings = optionalSettings.get();
            properties.put("hibernate.connection.username", settings.getUserName());
            properties.put("hibernate.connection.password", settings.getPassword());
            properties.put("hibernate.connection.url", settings.getServerWithInnerSettings());
            properties.put("hibernate.connection.driver_class", settings.getDriverClass());
        }
        entityManagerFactory = Persistence.createEntityManagerFactory("UserList", properties);
    }

    public static Optional<Settings> getActiveSettings() {
        return Optional.ofNullable(settings);
    }

    public static void initialize() {}

    public static EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void closeEntityManagerFactory(){
        entityManagerFactory.close();
    }
}
