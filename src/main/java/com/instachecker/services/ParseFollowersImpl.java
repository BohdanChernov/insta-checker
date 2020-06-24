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
public class ParseFollowersImpl implements ParseFollowers {
    private WebDriver webDriver;
    private String intagramUrlAccountToParse;
    private int followerCount;
    private int lastNumberOfElements;
    List<WebElement> followerElements = new ArrayList<>();
    List<String> followerElementsStrings = new ArrayList<>();
    @Autowired
    IntermediateService intermediateService;

    public List<String> getFollowerFromTab(WebDriver webDriver, String intagramUrlAccountToParse) {
        this.webDriver = webDriver;
        this.intagramUrlAccountToParse = intagramUrlAccountToParse;
        openFollowersTab();
        getWebElementFollowers();
        return followerElementsStrings;
    }

    public void openFollowersTab() {
        try {
            intermediateService.waitTwoSec();
            String ccsSelector = ("a[href*=\"/" + intagramUrlAccountToParse + "/followers/\"]");
            WebElement followersButton = webDriver.findElement(By.cssSelector(ccsSelector));
            getTotalFollowers(followersButton);
            followersButton.click();
        } catch (Exception e) {
            e.printStackTrace();
            openFollowersTab();
        }
    }

    public void getTotalFollowers(WebElement followersLink) {
        String buttonTxt = followersLink.getText();
        followerCount = intermediateService.stayOnlyDigits(buttonTxt);
    }

    public void getWebElementFollowers() {
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
        if (followerCount == followerElements.size() || followerElements.size() == lastNumberOfElements) {
            return true;
        } else {
            lastNumberOfElements = followerElements.size();
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
        if (matcher.find() && !followerElements.contains(webElement) && !txt.contains("LANGUAGE")) {
            String userLogin = matcher.group(0).replace("\n", "");
            followerElementsStrings.add(userLogin);
            followerElements.add(webElement);
        }
    }

    public WebElement getLastElement() {
        try {
            return followerElements.get(followerElements.size() - 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
