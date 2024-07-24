package com.rbauction.testCases;

import com.rbauction.base.baseClass;
import com.rbauction.pageObjects.homePage;
import com.rbauction.pageObjects.searchResultPage;
import com.rbauction.utilities.commonFunctions;
import com.rbauction.utilities.readConfig;
import org.checkerframework.checker.units.qual.C;
import org.testng.annotations.Test;

public class TC_01 extends baseClass {

    readConfig rcnfg;
    homePage hp;
    searchResultPage sp;
    commonFunctions cFun;

    @Test
    public void TC01(){
        driver.get(baseURL);
        cFun= new commonFunctions(driver);
        hp= new homePage(driver,cFun);
        sp= new searchResultPage(driver,cFun);
        rcnfg= new readConfig();
        hp.verifyHomePage();
        hp.searchAuto(rcnfg.getsearchVehicleName());
        sp.verifyRsltPage();
        int resultCount= sp.getRsltCnt();
        System.out.println("Total results retrieved "+resultCount);
        sp.checkFrstRsltNm(rcnfg.getsearchVehicleName());
        sp.filterYear(rcnfg.getfilterYearFrom(),rcnfg.getfilterYearTo());
        int filterRsltCnt= sp.getRsltCnt();
        System.out.println("Total results retrieved after filtering the years "+filterRsltCnt);
        if (resultCount>filterRsltCnt){
            System.out.println("Initial results returned were greater when compared to filtered results.");
        } else if (resultCount<filterRsltCnt) {
            System.out.println("Initial results returned were lesser when compared to filtered results.");
        }else{
            System.out.println("Initial results returned were equal to filtered results.");
        }

    }
}
