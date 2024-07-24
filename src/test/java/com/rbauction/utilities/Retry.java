package com.rbauction.utilities;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.rbauction.base.baseClass;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry extends baseClass implements IRetryAnalyzer {
    private int retryCount=0;
    private int maxRetryCount =1;

    public boolean retry(ITestResult result){
        if (retryCount<maxRetryCount){
            System.out.println("Retrying test "+result.getName()+"with status "
            +getResultStatusName(result.getStatus())+" for the "+(retryCount+1)+" time(s).");
            extentTest.info(MarkupHelper.createLabel("Retrying test "+result.getName()+"with status "
                    +getResultStatusName(result.getStatus())+" for the "+(retryCount+1)+" time(s).", ExtentColor.AMBER));
            retryCount++;
            return true;
        }
        return false;
    }

    public String getResultStatusName(int status){
        String resultName= null;
        if (status==1){
            resultName="SUCCESS";
        }
        if (status==2){
            resultName="FAILURE";
        }
        if (status==3){
            resultName="SKIP";
        }
        return resultName;
    }
}
