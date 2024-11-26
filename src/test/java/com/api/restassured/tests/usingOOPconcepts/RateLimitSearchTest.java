package com.api.restassured.tests.usingOOPconcepts;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class RateLimitSearchTest extends BaseRateLimit {

    @Test
    void resourcesSearchDefaultValuesAreCorrect() {
        RestAssured.get()
                .then()
                .rootPath("resources.search")
                    .body("limit", equalTo(10))
                    .body("remaining", lessThanOrEqualTo(10));
    }
}
