package com.api.restassured.tests.usingOOPconcepts;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class RateLimitCoreTest extends BaseRateLimit {

    @Test
    void resourcesCoreDefaultValuesAreCorrect() {
        RestAssured.get()
                .then()
                .rootPath("resources.core")
                    .body("limit", equalTo(60))
                    .body("remaining", lessThanOrEqualTo(60));
    }
}
