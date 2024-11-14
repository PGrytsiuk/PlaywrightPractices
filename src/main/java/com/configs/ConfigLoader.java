package com.configs;

import java.io.*;
import java.util.Properties;

public class ConfigLoader {

    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";
    private final Properties properties = new Properties();

    public ConfigLoader() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream inputStream = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load properties from " + CONFIG_FILE_PATH);
        }
    }

    public synchronized String getProperty(String key) {
        return properties.getProperty(key);
    }

    public synchronized void setProperty(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
    }

    private void saveProperties() {
        try (OutputStream outputStream = new FileOutputStream(CONFIG_FILE_PATH)) {
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save properties to " + CONFIG_FILE_PATH);
        }
    }
}