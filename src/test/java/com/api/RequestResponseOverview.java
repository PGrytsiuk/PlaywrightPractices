package com.api;

import com.common.hooks.BasicSetup;
import com.microsoft.playwright.Request;
import com.microsoft.playwright.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestResponseOverview extends BasicSetup {

    @Test
    public void responseApiDemo(){

        Response response = page.navigate("https://gym.langfit.net/login"); //.html
        System.out.println(response.url());
        System.out.println(response.status()); // 2000
        System.out.println(response.ok());
        System.out.println(response.headers());

        System.out.println(Arrays.toString(response.body()));
        System.out.println("Convert byte array");
        System.out.println(new String(response.body(), StandardCharsets.UTF_8 ));
        System.out.println("PW convince method");
        System.out.println(response.text());

    }

    @Test
    public  void requestApiDemo(){

        Response response = page.navigate("https://gym.langfit.net/login"); //.html
        Request request = response.request();

        System.out.println(request.headers());
        System.out.println(request.postData());
        System.out.println(request.method());

    }

    @Test
    public void monitorHttpTrafficDemo(){

       /* page.onRequest(request -> System.out.println(">> " + request.method() + " " + request.url()));
        page.onResponse(response -> System.out.println("<< "+ response.status()));*/

        List<Integer> responses = new ArrayList<>();
        page.onResponse(response -> responses.add(response.status()));

        page.navigate("https://gym.langfit.net/login");

        System.out.println(responses);

        boolean foundMatch = responses.stream()
                .anyMatch(i -> i < 200 || i >= 300);
        Assert.assertFalse(foundMatch);

    }

    @Test
    public void codeChallenge(){
        List<Boolean> responses = new ArrayList<>();
        page.onResponse(response ->{
            boolean isOk = response.ok();
            responses.add(isOk);
                    System.out.println("Response status: " + (isOk ? "OK" : "Not OK"));
                }
            );
        page.navigate("https://gym.langfit.net/login");

        System.out.println("List if responses status: " + responses);

        boolean foundNotOk = responses.stream().anyMatch(ok -> !ok);
        Assert.assertFalse(foundNotOk);

    }

}
