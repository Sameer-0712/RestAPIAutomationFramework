package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class RestUtils {

    public static Response performPost(String endpoint, String basePath, Map<String,String> headers){

        return RestAssured.given().log().all().baseUri(endpoint).basePath("{basepath}")
                .headers(headers)
                .contentType(ContentType.JSON).pathParam("basepath",basePath)
                .when().post()
                .then().log().all().extract().response();
    }


}
