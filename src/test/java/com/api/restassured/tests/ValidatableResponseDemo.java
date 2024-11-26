package com.api.restassured.tests;

import com.microsoft.playwright.Response;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import static org.hamcrest.Matchers.*;

public class ValidatableResponseDemo {

    static final String BASE_URL = "https://api.github.com";

    @Test
    void basicValidatableExample() {
        RestAssured.get(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .header("X-Xss-Protection", "0")
                .header("X-ratelimit-Limit", "60");

    }

    @Test
    void simpleHamcrestMatchers() {
        RestAssured.get(BASE_URL)
                .then()
                .statusCode(200)
                .statusCode(lessThan(300))
                .header("cache-control", containsStringIgnoringCase("max-age=60"))
                .time(lessThan(2L), TimeUnit.SECONDS)
                .header("etag", notNullValue())
                .header("etag", not(emptyString()));
    }

    Map<String, String> expectedHeaders = Map.of("content-encoding", "gzip",
            "access-control-allow-origin", "*");

    @Test
    public void usingMapsToTestHeaders() {
        RestAssured.get(BASE_URL)
                .then()
                .headers("content-encoding", "gzip",
                        "access-control-allow-origin", "*",
                        "cache-control", containsString("public"))
                .headers(expectedHeaders);
    }
}
