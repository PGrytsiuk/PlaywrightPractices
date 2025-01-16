package com.api.restassured.tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class ValidateAuthorization {

    private static String BASE_URL = "https://api.github.com/user";
    private static String TOKEN = "invalid token";

    @Test
    public void validateAuthorization() {

        RestAssured
                .given()
                .auth()
                .oauth2(TOKEN)
                .get(BASE_URL)
                .then()
                .statusCode(401)
                .body("message", org.hamcrest.Matchers.equalTo("Bad credentials"));
    }
}
