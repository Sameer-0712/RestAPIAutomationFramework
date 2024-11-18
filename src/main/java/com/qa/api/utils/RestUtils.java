package com.qa.api.utils;

import com.qa.api.constants.AuthType;
import com.qa.api.exception.FrameworkException;
import com.qa.api.manager.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;

public class RestUtils {

    String baseUri = ConfigManager.readConfigFile("baseUri");

    ResponseSpecification responseSpecification = expect().statusCode(anyOf(equalTo(200),equalTo(201),
            equalTo(204),equalTo(400)));

    private RequestSpecification setupRequest(AuthType authType, ContentType contentType){

        RestAssured.baseURI = baseUri;

        RequestSpecification requestSpecification =
                given().contentType(contentType);

        switch (authType){
            case BEARER_TOKEN:
                requestSpecification.header("Authorization", "Bearer "+ConfigManager.readConfigFile("bearerToken"));
                break;
            case OAUTH2:
                requestSpecification.auth().oauth2(getOauth2Token());
                break;
            case BASIC_AUTH:
                requestSpecification.auth().basic(ConfigManager.readConfigFile("username"),ConfigManager.readConfigFile("password"));
                break;
            case API_KEY:
                requestSpecification.header("x-api-key",ConfigManager.readConfigFile("apiKey"));
                break;
            case NO_AUTH:
                System.out.println("NO AUTH REQUIRED");
                break;
            default:
                throw new FrameworkException("Incorrect Auth Type passed");
        }
        return requestSpecification;

    }

    private String getOauth2Token() {

        Map<String, String> formData = Map.of("grant_type", "client_credentials",
                "client_id", ConfigManager.readConfigFile("clientId"),
                "client_secret", ConfigManager.readConfigFile("clientSecret"));

        Response response = RestAssured.given()
                .formParams(formData)
                .when().log().all()
                .post("/v1/security/oauth2/token")
                .then().log().all()
                .extract().response();

        return response.jsonPath().getString("access_token");

    }

    private void applyParams(RequestSpecification requestSpecification, Map<String,String> pathParams, Map<String,String> queryParams){
        if(pathParams != null){
            requestSpecification.pathParams(pathParams);
        }

        if(queryParams != null){
            requestSpecification.queryParams(queryParams);
        }
    }

    /**
     * Generic method to do a post call. This method will be utilised in Test Classes.
     * @param endpoint
     * @param body
     * @param pathParams
     * @param queryParams
     * @param authType
     * @param contentType
     * @return Returns the Response Object
     * @param <T>
     */
    public <T>Response performPost(String endpoint, T body, Map<String,String> pathParams, Map<String,String> queryParams, AuthType authType, ContentType contentType){

        applyParams(setupRequest(authType,contentType),pathParams,queryParams);

        Response response = setupRequest(authType,contentType).body(body).post(endpoint).then().spec(responseSpecification).extract().response();
        response.prettyPrint();
        return response;
    }

    /**
     * Generic overloaded method to do a post call. This method will be utilised when user need to pass File type object in body method while doing the post call
     * @param endpoint
     * @param file
     * @param pathParams
     * @param queryParams
     * @param authType
     * @param contentType
     * @return Returns the Response Object
     */
    public Response performPost(String endpoint, File file, Map<String,String> pathParams, Map<String,String> queryParams, AuthType authType, ContentType contentType){

        applyParams(setupRequest(authType,contentType),pathParams,queryParams);

        Response response = setupRequest(authType,contentType).body(file).post(endpoint).then().spec(responseSpecification).extract().response();
        response.prettyPrint();
        return response;
    }

    /**
     * Generic method to do a get call. This method will be utilised in Test Classes.
     * @param endpoint
     * @param pathParams
     * @param queryParams
     * @param authType
     * @param contentType
     * @return Returns the Response Object
     */
    public Response performGet(String endpoint, Map<String,String> pathParams, Map<String,String> queryParams, AuthType authType, ContentType contentType){

        applyParams(setupRequest(authType,contentType),pathParams,queryParams);

        return setupRequest(authType,contentType).
                when()
                .get(endpoint)
                .then()
                .spec(responseSpecification)
                .extract().response();

    }

}
