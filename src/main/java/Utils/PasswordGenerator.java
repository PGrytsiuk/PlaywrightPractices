package Utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class PasswordGenerator {

    private static final String PROPERTIES_FILE_PATH = "config.properties";
    private static final String COUNTER_KEY = "passwordCounter";
    private static final String PREFIX_KEY = "passwordPrefix";
    private static final String LATEST_PASSWORD_KEY = "latestPassword";

    public static String generateUniquePassword() {
        int counter = readCounter();
        counter++;
        writeCounter(counter);
        String prefix = readPrefix();
        String newPassword = prefix + counter;
        writeLatestPassword(newPassword);
        return newPassword;
    }

    private static int readCounter() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(input);
            String counter = properties.getProperty(COUNTER_KEY, "0");
            return Integer.parseInt(counter);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading properties file. Using default value 0.");
            return 0;
        }
    }

    private static String readPrefix() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(input);
            return properties.getProperty(PREFIX_KEY, "Password");
        } catch (IOException e) {
            System.err.println("Error reading properties file. Using default prefix 'Password'.");
            return "Password";
        }
    }

    private static void writeCounter(int counter) {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading properties file.");
        }
        properties.setProperty(COUNTER_KEY, String.valueOf(counter));
        try (FileOutputStream output = new FileOutputStream(PROPERTIES_FILE_PATH)) {
            properties.store(output, null);
        } catch (IOException e) {
            System.err.println("Error writing to properties file.");
        }
    }

    private static void writeLatestPassword(String newPassword) {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading properties file.");
        }
        properties.setProperty(LATEST_PASSWORD_KEY, newPassword);
        try (FileOutputStream output = new FileOutputStream(PROPERTIES_FILE_PATH)) {
            properties.store(output, null);
        } catch (IOException e) {
            System.err.println("Error writing to properties file.");
        }
    }



    public static void main(String[] args) {
        String newPassword = generateUniquePassword();
        System.out.println("Generated Password: " + newPassword);
    }
}
