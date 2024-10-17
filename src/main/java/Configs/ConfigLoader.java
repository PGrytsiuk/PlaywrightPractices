package Configs;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigLoader {

    private static final String CONFIG_FILE_PATH = "config.properties";
    private final Properties properties = new Properties();

    public ConfigLoader() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_PATH)) {
            if (inputStream == null) {
                throw new RuntimeException("Property file not found at " + CONFIG_FILE_PATH);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load properties from " + CONFIG_FILE_PATH);
        }
    }


    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}