package com.instachecker.services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ParseFollowingsImpl implements ParseFollowings{
    private WebDriver webDriver;
    private String intagramUrlAccountToParse;
    private int followingCount;
    private int lastNumberOfElements;
    List<WebElement> followingElements = new ArrayList<>();
    List<String> followingElementsStrings = new ArrayList<>();
    @Autowired
    IntermediateService intermediateService;

    @Override
    public List<String> getFollowingsFromTab(WebDriver webDriver, String intagramUrlAccountToParse) {
        this.webDriver = webDriver;
        this.intagramUrlAccountToParse = intagramUrlAccountToParse;
        openFollowingsTab();
        getWebElementFollowings();
        return followingElementsStrings;
    }

    public void openFollowingsTab() {
        try {
            intermediateService.waitTwoSec();
            String ccsSelector = ("a[href*=\"/" + intagramUrlAccountToParse + "/following/\"]");
            WebElement followingsButton = webDriver.findElement(By.cssSelector(ccsSelector));
            getTotalFollowings(followingsButton);
            followingsButton.click();
        } catch (Exception e) {
            e.printStackTrace();
            openFollowingsTab();
        }
    }

    public void getTotalFollowings(WebElement followingsLink) {
        String buttonTxt = followingsLink.getText();
        followingCount = intermediateService.stayOnlyDigits(buttonTxt);
    }

    public void getWebElementFollowings() {
        lastNumberOfElements = 0;
        intermediateService.waitTwoSec();
        while (true) {
            findElementsAndScroll();
            if (isFinishedLoading()) {
                break;
            }
        }
    }

    public boolean isFinishedLoading() {
        if (followingCount == followingElements.size() || followingElements.size() == lastNumberOfElements) {
            return true;
        } else {
            lastNumberOfElements = followingElements.size();
            return false;
        }
    }

    public void findElementsAndScroll() {
        List<WebElement> userElements = webDriver.findElements(By.tagName("li"));
        userElements.forEach(webElement -> putInStorageIfUser(webElement));
        intermediateService.scrollDownOpenedTab(webDriver, getLastElement());
    }

    public void putInStorageIfUser(WebElement webElement) {
        String txt = null;
        try {
            txt = webElement.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pattern pattern = Pattern.compile(".+\\n");
        Matcher matcher = pattern.matcher(txt);
        if (matcher.find() && !followingElements.contains(webElement) && !txt.contains("LANGUAGE")) {
            String userLogin = matcher.group(0).replace("\n", "");
            followingElementsStrings.add(userLogin);
            followingElements.add(webElement);
        }
    }

    public WebElement getLastElement() {
        try {
            return followingElements.get(followingElements.size() - 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
