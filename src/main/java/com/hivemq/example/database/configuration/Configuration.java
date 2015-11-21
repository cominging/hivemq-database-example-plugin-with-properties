package com.hivemq.example.database.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * This is created by maofan on 11/21/2015.
 */
public class Configuration {
    String result = "";
    InputStream inputStream;
    Properties prop;

    public Configuration() {
        prop = new Properties();
        try {
            this.loadPropertiesValues();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public String loadPropertiesValues() throws IOException {
        try {
            String propFileName = "database.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return result;
    }

    public String get(String key) {
        return prop.getProperty(key);
    }
}
