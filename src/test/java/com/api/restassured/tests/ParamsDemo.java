package com.api.restassured.tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.util.Map;

public class ParamsDemo {

    static final String BASE_URL = "https://api.github.com/search/repositories";

    //"user_search_url": "https://api.github.com/search/users?q={query}{&page,per_page,sort,order}"
    //https://api.github.com/search/users?q=java
    //https://api.github.com/search/users?q=java&per_page=1

    @Test
    void withoutParam() {

        RestAssured
                .get(BASE_URL + "?q=java")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void withoutParam2() {

        RestAssured
                .get(BASE_URL + "?q=java&per_page=1")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void withParam() {

        RestAssured
                .given()
                .param("q", "java")
                .param("per_page", "1")
                .get(BASE_URL)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void withParamAsMap() {

        RestAssured
                .given()
                .params(Map.of("q", "java", "per_page", "1"))
                .get(BASE_URL)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
