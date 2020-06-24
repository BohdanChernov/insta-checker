package com.instachecker.services;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

@Service
public interface IntermediateService {
    void waitTwoSec();
    void waitOneSec();
    int stayOnlyDigits(String buttonTxt);
    void scrollDownOpenedTab(WebDriver webDriver, WebElement elementToScroll);
}
