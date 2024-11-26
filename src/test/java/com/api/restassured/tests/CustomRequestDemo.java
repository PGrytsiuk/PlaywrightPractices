package com.api.restassured.tests;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.containsString;

public class CustomRequestDemo {

    static final String BASE_URL = "https://api.github.com/";

    @Test
    void customRequest() {
        //both equivalent to REstAssured.get(..)
        RestAssured.request(Method.GET, BASE_URL)
                .then()
                .statusCode(200)
                .body(containsString("current_user_url"));

        RestAssured.request("GET", BASE_URL)
                .then()
                .statusCode(200)
                .body(containsString("current_user_url"));
    }

    @Test
    void traceExample() {
        RestAssured.request(Method.TRACE, BASE_URL)
                .then()
                .statusCode(405); //Method Not Allowed
    }
}
