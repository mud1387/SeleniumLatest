package com.rbauction.pageObjects;

import com.rbauction.base.baseClass;
import com.rbauction.utilities.commonFunctions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class searchResultPage extends baseClass {
    WebDriver ldriver;
    commonFunctions cf;
    public searchResultPage(WebDriver rdriver,commonFunctions cFun){
        ldriver=rdriver;
        PageFactory.initElements(rdriver,this);
        cf=cFun;
    }

    @FindBy(xpath = "//button[@data-testid='clear-button']")
    WebElement clearAll;
    @FindBy(xpath = "//h2[@data-testid='non-cat-header']")
    WebElement rsltCnt;
    @FindBy(xpath = "//ul[@id='searchResults']/div/div[1]//li//h4")
    WebElement frstRslt;
    @FindBy(xpath = "//div[normalize-space()='Year']")
    WebElement year;
    @FindBy(xpath = "//input[@id='manufactureYearRange_min']")
    WebElement yearFrom;
    @FindBy(xpath = "//input[@id='manufactureYearRange_max']")
    WebElement yearTo;
    @FindBy(xpath = "//div[@id='manufactureYearRange']//div/div")
    WebElement fltrYear;

    public boolean verifyRsltPage(){
        new WebDriverWait(ldriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(clearAll));
        return clearAll.isDisplayed();
    }
    public boolean verifyRsltCnt(){
        new WebDriverWait(ldriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(rsltCnt));
        return rsltCnt.isDisplayed();
    }
    public boolean verifyFltrRsltCnt(){
        new WebDriverWait(ldriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(fltrYear));
        return fltrYear.isDisplayed();
    }
    public int getRsltCnt(){
        verifyRsltCnt();
        String txt= rsltCnt.getText();
        String[] arr= txt.substring(txt.indexOf("of"),txt.indexOf("results")).split(" ");
        return Integer.parseInt(arr[1]);
    }

    public void checkFrstRsltNm(String txt){
        if (frstRslt.isDisplayed()){
            if (frstRslt.getText().contains(txt)){
                System.out.println("Name found in first result: "+frstRslt.getText()
                +" contains the value "+txt);
            }else{
                System.out.println("Name comparison failed");
            }
        }
    }
    public void filterYear(String from, String to){
        cf.scrollIntoViewJS(year);
        clearText(yearFrom);
        yearFrom.sendKeys(from);
        verifyRsltCnt();
        clearText(yearTo);
        yearTo.sendKeys(to);
        yearTo.sendKeys(Keys.ENTER);
        verifyFltrRsltCnt();

    }

    public void clearText(WebElement ele){
/*        ele.click();
        Actions actions= new Actions(ldriver);
        actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL);
        actions.perform();
        actions.sendKeys(Keys.DELETE);
        actions.perform();*/
        ele.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
    }

}
