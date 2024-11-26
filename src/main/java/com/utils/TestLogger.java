package com.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogger {

    private static final Logger logger = LoggerFactory.getLogger(TestLogger.class);

    public static void main(String[] args) {

        // Log messages at different levels
        logger.trace("This is a TRACE level message");
        logger.debug("This is a DEBUG level message");
        logger.info("This is an INFO level message");
        logger.warn("This is a WARN level message");
        logger.error("This is an ERROR level message");

        // Example of logging with parameters
        String param1 = "Parameter1";
        String param2 = "Parameter2";
        logger.info("Logging with parameters: {}, {}", param1, param2);
    }

}
