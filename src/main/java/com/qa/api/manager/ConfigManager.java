package com.qa.api.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

    static Properties properties = new Properties();

    static{
        try {
            FileInputStream fis = new FileInputStream("./src/test/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readConfigFile(String key){
        return properties.getProperty(key);
    }

}
