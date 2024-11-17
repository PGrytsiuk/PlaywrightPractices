package com.langfit.data;


import com.common.configs.ConfigLoader;

public class TestData {

    private static ConfigLoader config = new ConfigLoader();

    public static void reloadConfig() {
        config = new ConfigLoader();
    }

    public static String getValidUsername() {
        return config.getProperty("Valid_username");
    }

    public static String getLastPassword() {
        return config.getProperty("Latest_password");
    }

    public static String getInvalidUsername() {
        return config.getProperty("Invalid_username");
    }

    public static String getInvalidPassword() {
        return config.getProperty("Invalid_password");
    }

    public static String getEmailUsername() {
        return config.getProperty("Email_username");
    }

    public static String getEmailPassword() {
        return config.getProperty("Email_password");
    }

    // Add more test data as needed
}

