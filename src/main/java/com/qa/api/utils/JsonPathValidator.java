package com.qa.api.utils;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import io.restassured.response.Response;


public class JsonPathValidator {

    public static <T> T read(Response response, String path){
        ReadContext context = JsonPath.parse(response.asString());
        return context.read(path);
    }

}
