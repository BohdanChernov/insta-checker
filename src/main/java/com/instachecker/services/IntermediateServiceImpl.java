package com.instachecker.services;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IntermediateServiceImpl implements IntermediateService {

    @Override
    public void waitOneSec() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void waitTwoSec() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int stayOnlyDigits(String buttonTxt) {
        int onlyDigits = 0;
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(buttonTxt);
        if (matcher.find()) {
            onlyDigits = Integer.parseInt(matcher.group(0));
        }
        return onlyDigits;
    }

    @Override
    public void scrollDownOpenedTab(WebDriver webDriver, WebElement elementToScroll) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].scrollIntoView(true);", elementToScroll);
        waitTwoSec();
    }

}
