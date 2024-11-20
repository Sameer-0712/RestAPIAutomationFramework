package base;

import com.qa.api.manager.ConfigManager;
import com.qa.api.utils.RestUtils;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class BaseTest {

    protected RestUtils restUtils;
    protected Response response;


    @Parameters({"baseUrl"})
    @BeforeTest
    public void setup(String baseUrl){

        if(baseUrl != null){
            ConfigManager.setConfigFile("baseUrl",baseUrl);
        }
        restUtils = new RestUtils();
    }

}
