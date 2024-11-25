package com.ps.restassured;

import io.restassured.RestAssured;
import io.restassured.config.FailureConfig;
import io.restassured.config.LogConfig;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.listener.ResponseValidationFailureListener;

public class ConfigFactory {

    public static RestAssuredConfig getDefaultConfig() {
         ResponseValidationFailureListener failureListener =
                 (reqSpec, resSpec, response) ->
                         System.out.printf("We have a failure, " + "response status was %s and the body contained: %s",
                                 response.getStatusCode(), response.body().asPrettyString());
         return RestAssured.config()
                 .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL))
                 .redirect(RedirectConfig.redirectConfig().maxRedirects(1))
                 .failureConfig(FailureConfig.failureConfig().failureListeners(failureListener));

    }
}
