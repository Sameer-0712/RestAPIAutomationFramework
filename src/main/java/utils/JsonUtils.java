package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonUtils {

    public static Map<String, String> getConfigData(){
        ObjectMapper objectMapper = new ObjectMapper();
        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\QA\\config.json";
        Map<String,String> configData;
        try {
            configData = objectMapper.readValue(new File(filePath),
                    new TypeReference<Map<String,String>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return configData;
    }

}
