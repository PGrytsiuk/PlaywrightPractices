package com.api.rest.assured.tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class PeekAndPrintDemo {

    static final String BASE_URL = "https://api.github.com";

    @Test
    void peek() {
        RestAssured.get(BASE_URL)
                .peek();
    }

    @Test
    void prettyPeek() {
        RestAssured.get(BASE_URL)
                .prettyPeek();
    }

    @Test
    void print() {
        RestAssured.get(BASE_URL)
                .print();
    }

    @Test
    void prettyPrint() {
        RestAssured.get(BASE_URL)
                .prettyPrint();
    }
}
