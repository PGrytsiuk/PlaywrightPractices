package com.api.restassured.tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class DemoTest {

    @Test
    void someTest(){
        RestAssured.get("https://api.github.com")
                .then()
                .statusCode(200);
    }
}
