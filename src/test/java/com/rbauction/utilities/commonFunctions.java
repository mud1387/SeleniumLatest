package com.rbauction.utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class commonFunctions {
    public static String scrnshtSubFldNm;
    WebDriver driver;
    public commonFunctions(WebDriver driver){this.driver= driver;}
    public String captureScrnsht(String fileName) throws IOException {
        if (scrnshtSubFldNm==null){
            LocalDateTime myDateObj= LocalDateTime.now();
            DateTimeFormatter myFormatObj= DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            scrnshtSubFldNm= myDateObj.format(myFormatObj);
        }
        TakesScreenshot takesScreenshot= (TakesScreenshot) driver;
        File sourceFile= takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destFile= new File(System.getProperty("user.dir")+"\\screenshots\\"+scrnshtSubFldNm+"\\"+fileName);
        FileUtils.copyFile(sourceFile,destFile);
        System.out.println("Screenshot saved successfully.");
        return destFile.toString();
    }

    public void scrollIntoViewJS(WebElement ele){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(ele));
        JavascriptExecutor executor=  (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView();",ele);
    }
}
