package com.api.rest.assured.tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class PathParamsDemo {

    static final String REPO_EP = "https://api.github.com/repos";
    static final String PGRYTSIUK = "/Pgrytsiuk";
    static final String REPO_PLAYWRIGHT = "/PlaywrightPractices";
    static final String REPO_PRACTICING = "/Practicing";

    @Test
    void withoutParamWithConcatenation() {
        RestAssured
                .get(REPO_EP + PGRYTSIUK + REPO_PLAYWRIGHT)
                .then()
                .statusCode(200)
                .body("id", equalTo(860106649));

        RestAssured
                .get(REPO_EP + PGRYTSIUK + REPO_PRACTICING)
                .then()
                .statusCode(200)
                .body("id", equalTo(860671314));

        RestAssured
                .get(String.format("https://api.github.com/repos/%s/%s", "Pgrytsiuk", "PlaywrightPractices"))
                .then()
                .statusCode(200)
                .body("id", equalTo(860106649));
    }


}
