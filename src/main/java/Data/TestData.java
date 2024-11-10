package Data;


import Configs.ConfigLoader;

public class TestData {

    private static ConfigLoader config = new ConfigLoader();

    public static final String VALID_USERNAME = config.getProperty("Valid_username");
    public static final String VALID_PASSWORD = config.getProperty("Valid_password");
    public static final String INVALID_USERNAME = config.getProperty("Invalid_username");
    public static final String INVALID_PASSWORD = config.getProperty("Invalid_password");

    // Add more test data as needed
}

