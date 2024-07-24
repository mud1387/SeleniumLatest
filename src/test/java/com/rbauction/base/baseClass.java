package com.rbauction.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.rbauction.utilities.commonFunctions;
import com.rbauction.utilities.readConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class baseClass {

    readConfig rc= new readConfig();
    commonFunctions cf;
    public String baseURL= rc.getStringbaseURL();
    public String mthdNm;
    public static WebDriver driver;
    public static Logger log;
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;

    @Parameters("browser")
    @BeforeTest
    public void setup(String br){
        System.setProperty("log4j.configurationFile","src/test/resources/configuration/log4j2-test.properties");
        log= LogManager.getLogger();
        if (br.equals("chrome")){
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions() ;
            options.addArguments("--disable-notifications");
            driver= new ChromeDriver(options);
        }
        else if(br.equals("edge")){
            WebDriverManager.edgedriver().setup();
            driver= new EdgeDriver();}
        driver.manage().window().maximize();;
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
        extentTest= extentReports.createTest("TC01");
        log.info("Initialization completed for test execution start.");
        extentTest.info("Initialization completed for test execution start.");
}

    @BeforeMethod
    public void methodSetup(Method m){
        mthdNm= m.getName();
    }

    @AfterMethod
    public void checkStatus(Method m, ITestResult result) throws IOException {
        if (result.getStatus()==ITestResult.FAILURE){
            cf= new commonFunctions(driver);
            String scrnshtPath=null;
            scrnshtPath=cf.captureScrnsht(result.getTestContext().getName()+"_"+result.getName()+".png");
            System.out.println("Screenshot path: "+scrnshtPath);
            extentTest.addScreenCaptureFromPath(scrnshtPath);
            extentTest.fail(result.getThrowable());
        } else if (result.getStatus()==ITestResult.SUCCESS) {
            extentTest.pass(m.getName()+" is passed.");
        }
    }

    @AfterTest
    public void tearDown() throws IOException {
        driver.close();
        driver.quit();
        Runtime.getRuntime().exec("taskkill /F /IM ChromeDriver.exe");
        log.info("Test execution post processing completed.");
    }

    @BeforeSuite
    public void initExtentRept() throws IOException {
        String timeStamp= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String repName= "Test-Report-"+timeStamp+".html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"\\test-output\\"+repName);
        extentReports= new ExtentReports();
        sparkReporter.loadXMLConfig(System.getProperty("user.dir")+"\\src\\test\\resources\\configuration\\extent-config.xml");
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Host Name","localhost");
        extentReports.setSystemInfo("Environment","Test");
    }

    @AfterSuite
    public void genExtentRept(){
        extentReports.flush();
    }
}
