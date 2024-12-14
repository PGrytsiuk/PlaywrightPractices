package com.api.plurasight;

import com.pluralsight.enteties.User;
import com.pluralsight.handlers.JsonBodyHandler;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpClient.newBuilder;

public class Java11GetBodyTest {

    private static final String BASE_URL = "https://api.github.com/";

    @Test
    void bodyContainsCurrentUserURL() throws IOException, InterruptedException {
        //Arrange - create client
        HttpClient httpClient = newBuilder().build();

        //Arrange - crate request
        HttpRequest get = HttpRequest.newBuilder(URI.create(BASE_URL +"users/PGrytsiuk"))
                .setHeader("User-Agent", "PlaywrightPractices")
                .build();

        //Act -send request
        HttpResponse<String> response = httpClient.send(get, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        //Assert
        Assert.assertTrue(body.contains("\"login\":\"PGrytsiuk\""));
    }

    @Test
    void handleJsonBody() throws IOException, InterruptedIOException, InterruptedException {

        //Arrange - create client
        HttpClient httpClient = newBuilder().build();

        //Arrange - create request
        //Arrange - crate request
        HttpRequest get = HttpRequest.newBuilder(URI.create(BASE_URL +"users/PGrytsiuk"))
                .setHeader("User-Agent", "PlaywrightPractices")
                .build();

        HttpResponse<User> response = httpClient.send(get, JsonBodyHandler.jsonBodyHandler(User.class));

        String actualLogin = response.body().getLogin();

        Assert.assertEquals("PGrytsiuk", actualLogin);
    }
}
