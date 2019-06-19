package com.ff.postpone.task.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author Demo_Liu
 * @Date 2019/4/3 10:04
 * @description
 */
public class PropertiesUtil {

    private  Properties prpt;

    public PropertiesUtil(){
        ClassLoader loader = this.getClass().getClassLoader();
        InputStream inputStream = loader.getResourceAsStream("Properties.properties");
        prpt = new Properties();
        try {
            prpt.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  String getProperty(String property){
        return prpt.getProperty(property);
    }
}
