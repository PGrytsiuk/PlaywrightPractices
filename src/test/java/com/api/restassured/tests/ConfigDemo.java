package com.api.restassured.tests;

import io.restassured.RestAssured;
import io.restassured.config.FailureConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.listener.ResponseValidationFailureListener;
import org.testng.annotations.Test;

import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.hamcrest.Matchers.equalTo;

public class ConfigDemo {

    static final String BASE_URL = "https://api.github.com/";

    @Test
    void maxRedirectsTest() {

        RestAssured.config = RestAssured.config()
                        .redirect(redirectConfig().followRedirects(true).maxRedirects(1));

        RestAssured.get(BASE_URL + "repos/twitter/bootstrap")
                .then()
                .statusCode(equalTo(200));
    }

    @Test
    void failureConfigDemo() {

        ResponseValidationFailureListener failureListener =
            (reqSpec, resSpec, response) ->
                    System.out.printf("We have a failure, " + "response status was %s and the body contained: %s",
                            response.getStatusCode(), response.body().asPrettyString());
        RestAssured.config = RestAssured.config()
                .failureConfig(FailureConfig.failureConfig().failureListeners(failureListener));

        RestAssured.get(BASE_URL + "users/PGrytsiuk")
                .then()
                .body("bio", equalTo("I'm a Quality Engineer with 9 years of experience, and I enjoy writing tests in Java."));

    }
}
