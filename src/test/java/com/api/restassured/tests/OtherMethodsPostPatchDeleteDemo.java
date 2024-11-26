package com.api.restassured.tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class OtherMethodsPostPatchDeleteDemo {

    static final String REPOS_EP = "https://api.github.com/user/repos";
    static final String TOKEN = "SetYourToken";

    @Test(description = "Create a repo")
    void postTest() {
        RestAssured
                .given()
                .auth()
                .oauth2(TOKEN)
                  /*  .header("Authorization", "token " + TOKEN)*/
                    .body("{\"name\" : \"delete\"}")
                .when()
                    .post(REPOS_EP)
                .then()
                .statusCode(201);
    }

    @Test(description = "Update a repo")
    void patchTest() {
        RestAssured
                .given()
                    .header("Authorization", "token " + TOKEN)
                    .body("{\"name\" : \"delete-patched\"}")
                .when()
                    .patch("https://api.github.com/repos/PGrytsiuk/delete")
                .then()
                .statusCode(200);
    }
    @Test(description = "Delete a repo")
    void deleteTest() {
        RestAssured
                .given()
                    .header("Authorization", "token " + TOKEN)
                .when()
                    .delete("https://api.github.com/repos/PGrytsiuk/delete-patched")
                .then()
                .statusCode(204);
    }
}
