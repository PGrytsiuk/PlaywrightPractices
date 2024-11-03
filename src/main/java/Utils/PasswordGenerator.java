package Utils;

import Configs.ConfigLoader;

public class PasswordGenerator {

    private static final String COUNTER_KEY = "passwordCounter";
    private static final String PREFIX_KEY = "passwordPrefix";
    private static final String LATEST_PASSWORD_KEY = "latestPassword";

    private final ConfigLoader configLoader;

    public PasswordGenerator(){
        configLoader = new ConfigLoader();
    }

    public String generateUniquePassword() {
        int counter = readCounter();
        counter++;
        writeCounter(counter);
        String prefix = readPrefix();
        String newPassword = prefix + counter;
        writeLatestPassword(newPassword);
        return newPassword;
    }

    private int readCounter() {
            String counterStr = configLoader.getProperty(COUNTER_KEY);
            return counterStr == null ? 0 : Integer.parseInt(counterStr);
    }

    private String readPrefix() {
            return configLoader.getProperty(PREFIX_KEY) == null ? "Password" : configLoader.getProperty(PREFIX_KEY);
    }

    private void writeCounter(int counter) {
        configLoader.setProperty(COUNTER_KEY, String.valueOf(counter));
    }

    private void writeLatestPassword(String newPassword) {
        configLoader.setProperty(LATEST_PASSWORD_KEY, newPassword);
    }

    public static void main(String[] args) {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String newPassword = passwordGenerator.generateUniquePassword();
        System.out.println("Generated Password: " + newPassword);

    }
}
