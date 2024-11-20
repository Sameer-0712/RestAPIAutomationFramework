package com.qa.api.utils;

public class StringUtils {

    public static String createRandomEmail(){
        return "api_automation_"+System.currentTimeMillis()+"@gmail.com";
    }

}
