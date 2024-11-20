package com.qa.api.tests;

import base.BaseTest;
import com.qa.api.constants.AuthType;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroceryStoreTests extends BaseTest {


    @Test
    public void getStatusTest(){

        response =  restUtils.performGet("/status",null,null, AuthType.NO_AUTH, ContentType.JSON);
        String status = response.jsonPath().getString("status");

        Assert.assertEquals(status,"UP");

    }
}
