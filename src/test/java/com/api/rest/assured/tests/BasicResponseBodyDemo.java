package com.api.rest.assured.tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;


public class BasicResponseBodyDemo {

    //  "rate_limit_url": "https://api.github.com/rate_limit",
    public static final String LIMIT_EP = "https://api.github.com/rate_limit";

    @Test
    public void jsonPathReturnsMap() {
        Response response = RestAssured.get(LIMIT_EP);

        ResponseBody<?> body = response.body();
        JsonPath jsonPath = body.jsonPath();

        JsonPath jsonPath2 = response.body().jsonPath();

        Map<String, String> fullJson = jsonPath2.get();
        Map<String, String> subMap = jsonPath2.get("resources");
        Map<String, String> subMap2 = jsonPath2.getMap("resources.core");

        int value = jsonPath2.get("resources.core.limit");
        int value2 = jsonPath2.get("resources.graphql.remaining");

        System.out.println(fullJson);
        System.out.println(subMap);
        System.out.println(subMap2);
        System.out.println(value);
        System.out.println(value2);

        Assert.assertEquals(value, 60);
        Assert.assertEquals(value2, 60);
    }

    @Test
    public void castingFailure() {
        JsonPath jPAth = RestAssured.get(LIMIT_EP).body().jsonPath();

        Map<String, String> isNull = jPAth.get("incorrect.path"); //NPY
        int aMap = jPAth.get("resources.core"); //ClassCastException
        String value = jPAth.get("resources.core.limit"); //ClassCastException
    }
}