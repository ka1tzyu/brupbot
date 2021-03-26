package com.nkvl.app.classes;

import com.nkvl.app.App;
import org.apache.log4j.Level;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {
    public static void main(String[] args) {
        System.out.println(getPropertyValue("token"));
    }
    public static String getPropertyValue(String fileName, String property) {
        Properties props = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            props.load(is);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        App.logger.log(Level.INFO, String.format("Property [%s] was read from [%s]",
                property, fileName));
        return props.getProperty(property);
    }
    public static String getPropertyValue(String property) {
        return getPropertyValue(PathResolve.getPathTo("app.conf"), property);
    }
}
