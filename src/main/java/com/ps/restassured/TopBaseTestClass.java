package com.ps.restassured;

import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import static com.ps.restassured.ConfigFactory.getDefaultConfig;
import static io.restassured.RestAssured.DEFAULT_URI;

public class TopBaseTestClass {

    @BeforeSuite
    void suiteSetup() {
        RestAssured.config = getDefaultConfig();
    }

    @BeforeMethod
    void setup() {
        RestAssured.baseURI = "https: //api.github.com";

    }

    @AfterMethod
    void cleanup() {
        RestAssured.baseURI = DEFAULT_URI;
    }
}
