package com.api.rest.assured.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BasicResponseDemo {

    static final String BASE_URL = "https://api.github.com";

    @Test
    void convenienceMethods() {
        Response response = RestAssured.get(BASE_URL);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getContentType(), "application/json; charset=utf-8");
    }

    @Test
    void genericHeader() {
        Response response = RestAssured.get(BASE_URL);
        Assert.assertEquals(response.getHeader("server"), "github.com");
        Assert.assertEquals(response.getHeader("x-ratelimit-limit"), "60");

        //OR
        Assert.assertEquals(Integer.parseInt(response.getHeader("x-ratelimit-limit")), 60);
    }
}
