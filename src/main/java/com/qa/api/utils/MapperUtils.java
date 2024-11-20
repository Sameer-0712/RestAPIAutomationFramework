package com.qa.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

public class MapperUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    public static <T>T deserialize(Response response, Class<T> className){

        try {
            return mapper.readValue(response.asString(),className);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}
