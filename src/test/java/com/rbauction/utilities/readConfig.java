package com.rbauction.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

public class readConfig {
    Properties pro;

    public readConfig(){
        URL fl= this.getClass().getResource("/configuration/config.properties");
        try{
            URI uri= fl.toURI();
            File src= new File(uri);
            FileInputStream fis= new FileInputStream(src);
            pro= new Properties();
            pro.load(fis);
        } catch (Exception e) {
            System.out.println("Exception is: "+e.getMessage());
        }
    }

    public String getStringbaseURL(){return pro.getProperty("baseURL");}
    public String getsearchVehicleName(){return pro.getProperty("searchVehicleName");}
    public String getfilterYearFrom(){return pro.getProperty("filterYearFrom");}
    public String getfilterYearTo(){return pro.getProperty("filterYearTo");}
}
