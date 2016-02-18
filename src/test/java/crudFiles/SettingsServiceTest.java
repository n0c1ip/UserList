package crudFiles;

import crudDB.EntityManagerFactory;
import crudFiles.SettingsService;
import objects.Settings;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertFalse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Optional;


public class SettingsServiceTest {

    @Test
    public void shouldWriteSettings() {
        Settings settings = new Settings();
        settings.setServer("testWriteServer");
        settings.setUserName("testWriteUser");
        settings.setPassword("testWritePassword");
        String settingsFilePath = System.getProperty("user.dir") + "\\settingsTest";

        SettingsService.writeSettings(settings, settingsFilePath);
        File settingsFile = new File(settingsFilePath);

        Assert.assertTrue(settingsFile.exists());
        Assert.assertTrue(settingsFile.isFile());
        Assert.assertTrue(settingsFile.length() > 0);

        try {
            Files.delete(settingsFile.toPath());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void shouldReadSettings() {
        Settings expectedSettings = new Settings();
        expectedSettings.setServer("testReadServer");
        expectedSettings.setUserName("testReadUser");
        expectedSettings.setPassword("testReadPassword");
        String settingsFilePath = System.getProperty("user.dir") + "\\settingsTest";

        SettingsService.writeSettings(expectedSettings, settingsFilePath);
        Optional<Settings> optionalSettings = SettingsService.readSettings(settingsFilePath);
        Assert.assertTrue(optionalSettings.isPresent());
        Settings actualSettings = optionalSettings.get();

        Assert.assertEquals(expectedSettings.getServer(), actualSettings.getServer());
        Assert.assertEquals(expectedSettings.getUserName(), actualSettings.getUserName());
        Assert.assertEquals(expectedSettings.getPassword(), actualSettings.getPassword());

        File settingsFile = new File(settingsFilePath);
        try {
            Files.delete(settingsFile.toPath());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void settingsShouldBeInvalid() {
        Settings settings = new Settings();
        settings.setServer("invalidServerTest");
        settings.setUserName("invalidUserTest");
        settings.setPassword("invalidPasswordTest");
        Assert.assertFalse(SettingsService.isSettingsValid(settings));
    }

    @Test
    public void settingsShouldBeValid() {
        Optional <Settings> optionalSettings = EntityManagerFactory.getActiveSettings();
        if (optionalSettings.isPresent()) {
            Assert.assertTrue(SettingsService.isSettingsValid(optionalSettings.get()));
        }
    }

}