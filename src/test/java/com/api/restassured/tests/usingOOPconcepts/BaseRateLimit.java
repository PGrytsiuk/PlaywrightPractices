package com.api.restassured.tests.usingOOPconcepts;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static io.restassured.RestAssured.DEFAULT_PATH;
import static io.restassured.RestAssured.DEFAULT_URI;

public class BaseRateLimit {

    @BeforeMethod
    void setup() {
        RestAssured.baseURI = "https://api.github.com";
        RestAssured.basePath = "/rate_limit";
    }

    @AfterMethod
    void cleanup() {
        RestAssured.baseURI = DEFAULT_URI;
        RestAssured.basePath = DEFAULT_PATH;
    }
}
