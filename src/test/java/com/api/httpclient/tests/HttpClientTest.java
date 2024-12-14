package com.api.httpclient.tests;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.apache.http.entity.ContentType.getOrDefault;

public class HttpClientTest {

    CloseableHttpClient client;
    CloseableHttpResponse response;
    SoftAssert sa;

    @BeforeClass
    public void setup() throws IOException {
        //Arrange
        client =  HttpClientBuilder.create().build();
        //Act
        response = client.execute(new HttpGet("https://api.github.com/"));
        sa = new SoftAssert();

        int actualStatusCode = response.getStatusLine().getStatusCode();
        if(actualStatusCode != 200) {
            throw new SkipException("Basic criteria failed, " + "was expecting code 200, but got: " + actualStatusCode );
        }
    }

    @AfterClass
    public void cleanup() throws IOException {
        client.close();
        response.close();
    }

    @BeforeMethod
    public void logging(Method testMethod) {
        String desc = testMethod.getAnnotation(Test.class).description();

        System.out.println("Starting test: " + testMethod.getName() + " with description: " + desc);
    }

    @Test
    public void statusIs200() throws  IOException {
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    }

    @Test
    public void typeIsJson() throws IOException {
        Assert.assertEquals(getOrDefault(response.getEntity()).getMimeType(), "application/json");
    }

    @Test
    public void charSetIsUtf() throws IOException {
        Assert.assertEquals(getOrDefault(response.getEntity()).getCharset().toString(), "UTF-8");
    }

    @Test
    public void softAssertForHttpRequest() throws IOException {

        //Assert
        System.out.println("First assert");
        sa.assertEquals(response.getStatusLine().getStatusCode(), 200);

        System.out.println("Second assert");
        sa.assertEquals(getOrDefault(response.getEntity()).getMimeType(), "application/json");

        System.out.println("Third assert");
        sa.assertEquals(getOrDefault(response.getEntity()).getCharset().toString(), "UTF-8");

        sa.assertAll();
    }
}
