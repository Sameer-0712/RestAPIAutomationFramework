package com.qa.api.tests.amadeus;

import base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.JsonPathValidator;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class GetFlightDestinationsTest extends BaseTest {


    @Test(enabled = false)
    public void getFlightDestinationsTest(){

        response =  restUtils.performGet("/v1/shopping/flight-destinations",null,Map.of("origin","DEL"), AuthType.OAUTH2, ContentType.JSON);

    }

    @Test
    public void getFlightOffersTest(){

        String body = "{\n" +
                "  \"currencyCode\": \"EUR\",\n" +
                "  \"originDestinations\": [\n" +
                "    {\n" +
                "      \"id\": \"1\",\n" +
                "      \"originLocationCode\": \"NYC\",\n" +
                "      \"destinationLocationCode\": \"MAD\",\n" +
                "      \"departureDateTimeRange\": {\n" +
                "        \"date\": \"2024-12-01\",\n" +
                "        \"time\": \"15:00:00\"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"travelers\": [\n" +
                "    {\n" +
                "      \"id\": \"1\",\n" +
                "      \"travelerType\": \"ADULT\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"sources\": [\n" +
                "    \"GDS\"\n" +
                "  ],\n" +
                "  \"searchCriteria\": {\n" +
                "    \"maxFlightOffers\": 5,\n" +
                "    \"flightFilters\": {\n" +
                "      \"cabinRestrictions\": [\n" +
                "        {\n" +
                "          \"cabin\": \"BUSINESS\",\n" +
                "          \"coverage\": \"MOST_SEGMENTS\",\n" +
                "          \"originDestinationIds\": [\n" +
                "            \"1\"\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}\n";

        response =  restUtils.performPost("/v2/shopping/flight-offers",body,null,null, AuthType.OAUTH2, ContentType.JSON);

        List<Map<String,String>> list = JsonPathValidator.read(response,"$.data[*].price.fees[*][?(@.type == 'SUPPLIER')]");
        for(Map<String,String> item:list){
            System.out.println(item);
            System.out.println("--------------------");

        }

    }

}
