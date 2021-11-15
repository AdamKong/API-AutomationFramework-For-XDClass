package com.xdclass.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @program      : xdclassAPITest
 * @ Author      ：Fanyong Kong
 * @ Date        ：Created in 14:28 2021/11/13 2021
 * @ Description ：This is to read the base properties
 * @Version      : 1.0
 */
public class Testbase {

    public static String getProperty(String key){
        String configPath = "/src/main/java/com/xdclass/config/config.properties";
        String propValue = "";
        Properties prop = new Properties();
        try{
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+configPath);
            prop.load(fis);
            propValue = prop.getProperty(key);
        }catch(FileNotFoundException f){
            f.printStackTrace();
        }catch (IOException i){
            i.printStackTrace();
        }

        return propValue;
    }

}
