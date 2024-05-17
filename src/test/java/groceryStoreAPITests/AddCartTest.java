package groceryStoreAPITests;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.JsonUtils;
import utils.RestUtils;

import java.util.HashMap;
import java.util.Map;

public class AddCartTest {

    private Response res;
    private Map<String,String> resAsMap;

@BeforeClass
    public void createCart(){
        String baseURI = JsonUtils.getConfigData().get("endpoint");
        String basePath = "carts";
        res = RestUtils.performPost(baseURI,basePath,new HashMap<>());
        resAsMap = res.as(new TypeRef<Map<String,String>>() {
        });
    }

@Test
    public void verifyStatusCode(){
        Assert.assertEquals(res.statusCode(),201);
    }

@Test
    public void verifyCartIdToBeNonNull(){
        Assert.assertNotNull(resAsMap.get("cartId"));
    }

@Test
    public void verifyCreatedIsTrue(){
        Assert.assertTrue(Boolean.parseBoolean(resAsMap.get("created")));
    }
}



