package com.api.restassured.tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static com.ps.restassured.ResponseSpecs.badEndpointSpec;
import static org.hamcrest.Matchers.equalTo;

public class BadEndpointTest {

    static final String BAD_URL = "https://api.github.com/non/existing/url";

    @Test
    public void  testWithSpecOne() {

        RestAssured.get(BAD_URL)
                .then()
                .spec(badEndpointSpec())
                .body("message", equalTo("Not Found"));
    }

    @Test
    public void testWithSpecTwo(){

        RestAssured.get(BAD_URL)
            .then()
            .spec(badEndpointSpec())
            .body("documentation_url", equalTo("https://docs.github.com/rest"));
    }
}
