package com.rbauction.pageObjects;

import com.rbauction.base.baseClass;
import com.rbauction.utilities.commonFunctions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class homePage extends baseClass {

    WebDriver ldriver;
    commonFunctions cf;
    public homePage(WebDriver rdriver,commonFunctions cFun){
        ldriver=rdriver;
        PageFactory.initElements(rdriver,this);
        cf=cFun;
    }

    @FindBy(xpath = "//img[@alt='Ritchie Bros. Auctioneers logo']")
    WebElement logoText;

    @FindBy(xpath = "//div[@data-testid='search input']/input")
    WebElement searchBar;

    @FindBy(xpath = "//button[@value='search']")
    WebElement searchBarSubmit;

    @FindBy(xpath = "//span[normalize-space()='© 2024 Ritchie Bros. Auctioneers']")
    WebElement footerText;

    @FindBy(xpath = "//div[@id='truste-consent-buttons']")
    WebElement cookies;

    public boolean verifyHomePage(){
        acceptCookies();
        new WebDriverWait(ldriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(logoText));
        return logoText.isDisplayed();
    }
    public void acceptCookies(){
        new WebDriverWait(ldriver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOf(cookies));
        if (cookies.isDisplayed()){
            cookies.click();
        }
    }
    public boolean verifySearchBar(){
        new WebDriverWait(ldriver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOf(searchBar));
        return searchBar.isDisplayed();
    }
    public void setSearchText(String txt){
        try{
            if (verifySearchBar()){
                searchBar.click();
                searchBar.sendKeys(txt);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void clickSearch(){
        searchBarSubmit.click();
    }

    public void searchAuto(String txt){
        setSearchText(txt);
        clickSearch();
    }

}
