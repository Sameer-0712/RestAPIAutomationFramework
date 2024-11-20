package com.qa.api.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

    private static Properties properties = new Properties();

    static{
        try {
            FileInputStream fis = new FileInputStream("./src/test/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readConfigFile(String key){
        return properties.getProperty(key);
    }

    public static void setConfigFile(String key, String value){
        properties.setProperty(key,value);
    }

}
