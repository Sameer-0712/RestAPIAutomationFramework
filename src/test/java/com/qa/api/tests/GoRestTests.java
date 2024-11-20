package com.qa.api.tests;

import base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.GoRestUser;
import com.qa.api.utils.StringUtils;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class GoRestTests extends BaseTest {


    @Test
    public void getUsersTest(){

        response =  restUtils.performGet("/public/v2/users",null,null, AuthType.BEARER_TOKEN, ContentType.JSON);

    }

    @Test(enabled = false)
    public void getUsersFromPageTest(){

        Map<String,String> queryParams = null;

        int i = 1;
        Map<String, Integer> queryParam = null;
        JsonPath js = null;
        Response response = null;

        do {
            queryParams = Map.of("page",String.valueOf(i));

            response = restUtils.performGet("/public/v2/users",null,queryParams, AuthType.BEARER_TOKEN, ContentType.JSON);

            js = response.jsonPath();
            try{
                Assert.assertFalse(js.getList("id").isEmpty());
                System.out.println(String.format("Page Number %d verified successfully...", i));
            }catch (AssertionError e){
                System.out.println(String.format("Page Number %d verified successfully...", i));
                System.out.println("VALIDATION DONE!!");
            }

            System.out.println("===========================================================================");

            ++i;
        }

        while(!js.getList("id").isEmpty());

    }

    @Test
    public void E2ETest(){

       String body =  "{\n" +
                "    \"name\": \"Sam001\",\n" +
                "    \"email\": \"%s\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"status\": \"active\"\n" +
                "}";

       String updateBody = "{\n" +
               "    \"email\": \"%s\"\n" +
               "}";


        response =  restUtils.performPost("/public/v2/users",String.format(body, StringUtils.createRandomEmail()),null,null,AuthType.BEARER_TOKEN,ContentType.JSON);

        int id = response.jsonPath().getInt("id");

        restUtils.performGet("/public/v2/users/"+id,null,null, AuthType.BEARER_TOKEN, ContentType.JSON).prettyPrint();

        restUtils.performPatch("/public/v2/users/"+id,String.format(updateBody, "update_"+StringUtils.createRandomEmail()),null,null,AuthType.BEARER_TOKEN, ContentType.JSON);

        restUtils.performGet("/public/v2/users/"+id,null,null, AuthType.BEARER_TOKEN, ContentType.JSON).prettyPrint();

        restUtils.performDelete("/public/v2/users/"+id,null,null,AuthType.BEARER_TOKEN, ContentType.JSON);

        restUtils.performGet("/public/v2/users/"+id,null,null, AuthType.BEARER_TOKEN, ContentType.JSON).prettyPrint();

    }

    @Test
    public void E2ETestWithPOJO(){

        GoRestUser user = new GoRestUser("Samtest",StringUtils.createRandomEmail(),"male","active");
        GoRestUser updatedUser = new GoRestUser("Samtest","update_"+StringUtils.createRandomEmail(),"male","active");


        response =  restUtils.performPost("/public/v2/users",user,null,null,AuthType.BEARER_TOKEN,ContentType.JSON);

        int id = response.jsonPath().getInt("id");

        restUtils.performGet("/public/v2/users/"+id,null,null, AuthType.BEARER_TOKEN, ContentType.JSON).prettyPrint();

        restUtils.performPatch("/public/v2/users/"+id,updatedUser,null,null,AuthType.BEARER_TOKEN, ContentType.JSON);

        restUtils.performGet("/public/v2/users/"+id,null,null, AuthType.BEARER_TOKEN, ContentType.JSON).prettyPrint();

        restUtils.performDelete("/public/v2/users/"+id,null,null,AuthType.BEARER_TOKEN, ContentType.JSON);

        restUtils.performGet("/public/v2/users/"+id,null,null, AuthType.BEARER_TOKEN, ContentType.JSON).prettyPrint();

    }

}
