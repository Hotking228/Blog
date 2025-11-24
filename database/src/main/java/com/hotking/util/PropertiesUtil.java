package com.hotking.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();
    private static final String FILENAME = "application.properties";

    static {
        load();
    }

    private static void load(){
        try(var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(FILENAME)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key){
        return PROPERTIES.getProperty(key);
    }
}
