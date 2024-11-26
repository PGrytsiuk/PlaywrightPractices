package com.api.restassured.tests;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import org.testng.annotations.Test;

public class LoggingDemo {

    static final String POSTS_EP ="https://jsonplaceholder.typicode.com/posts";

    @Test
    void logTest() {
        RestAssured
                .given()
                    .log().all()
                //OR
                    .log().headers()
                    .log().body()
                .when()
                    .get(POSTS_EP)
                .then()
                .log().headers()
                .log().ifValidationFails(LogDetail.ALL)
                .statusCode(200);
    }

}
