package com.api.restassured.tests;

import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OtherMethodsPostPatchDeleteDemo {

    static final String REPOS_EP = "https://api.github.com/user/repos";
    static final String TOKEN = System.getenv("GITHUB_TOKEN");
    private static final List<Method> testMethods;
    private static int currentTestIndex = 0;

    static {
        testMethods = Arrays.stream(OtherMethodsPostPatchDeleteDemo.class.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Test.class))
                .collect(Collectors.toList());
    }

    @Test(description = "Create a repo")
    void postTest() {
        RestAssured
                .given()
                .auth()
                .oauth2(TOKEN)
//                    .header("Authorization", "token " + TOKEN)
                    .body("{\"name\" : \"delete\"}")
                .when()
                    .post(REPOS_EP)
                .then()
                .statusCode(201);

    }

    @Test(description = "Update a repo", dependsOnMethods = "postTest")
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
    @Test(description = "Delete a repo", dependsOnMethods = "patchTest")
    void deleteTest() {
        RestAssured
                .given()
                    .header("Authorization", "token " + TOKEN)
                .when()
                    .delete("https://api.github.com/repos/PGrytsiuk/delete-patched")
                .then()
                .statusCode(204);
    }

    @AfterMethod
    void waitForTheNextTest() throws InterruptedException {
        currentTestIndex++;
        if (currentTestIndex < testMethods.size()) {
            Thread.sleep(3000);
        }
    }
}
