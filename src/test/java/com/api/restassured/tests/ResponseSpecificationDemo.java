package com.api.restassured.tests;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.testng.annotations.Test;

public class ResponseSpecificationDemo {

    static final String BASE_URL = "https://api.github.com/";

    @BeforeClass
    public static void setup() {
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(404)
                .expectContentType(ContentType.JSON)
                .build();
    }

    @AfterClass
    public static void cleanup() {
        RestAssured.requestSpecification = null;
    }

    //or
    ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(404)
                .expectContentType(ContentType.JSON)
                .build();

    @Test
    void  testWithSpecOne() {

        RestAssured.get(BASE_URL + "non/existing/url")
                .then()
                .spec(responseSpec);

        //+ more custom verifications
    }

    @Test
    void testWithSpecTwo(){

        RestAssured.get(BASE_URL + "non/existing/url");
    }
}
