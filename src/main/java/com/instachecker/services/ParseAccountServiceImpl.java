package com.instachecker.services;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParseAccountServiceImpl implements ParseAccountService {
    private WebDriver webDriverForFollowers;
    private WebDriver webDriverForFollowings;
    private Thread threadFollowers;
    private Thread threadFollowings;
    private String intagramUrlAccountToParse;
    private List<String> followers;
    private List<String> following;
    @Value("${inttagram.link}")
    private String intagramLink;
    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private ParseFollowers parseFollowers;
    @Autowired
    private ParseFollowings parseFollowings;

    @Override
    public void parseAccount(String url) {
        intagramUrlAccountToParse = url;
        webDriverForFollowers = connectionService.getWebDriver();
        webDriverForFollowings = connectionService.getWebDriver();
        runFollowersThread();
        runFollowingThread();
        waitToFinish();
    }

    public void waitToFinish() {
        try {
            threadFollowers.join();
            threadFollowings.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void runFollowersThread() {
        threadFollowers = new Thread(() -> setFollowers());
        threadFollowers.setName("FollowersResearchThread");
        threadFollowers.start();
    }

    public void runFollowingThread() {
        threadFollowings = new Thread(() -> setFollowings());
        threadFollowings.setName("FollowingResearchThread");
        threadFollowings.start();
    }

    public void openAccountPage(WebDriver webDriver) {
        webDriver.get(intagramLink + intagramUrlAccountToParse);
    }

    public void setFollowers() {
        openAccountPage(webDriverForFollowers);
        followers = parseFollowers.getFollowerFromTab(webDriverForFollowers, intagramUrlAccountToParse);
    }

    public void setFollowings() {
        openAccountPage(webDriverForFollowings);
        following = parseFollowings.getFollowingsFromTab(webDriverForFollowings, intagramUrlAccountToParse);
    }

    public List<String> getFollowers() {

        return followers;
    }

    public List<String> getFollowing() {
        return following;
    }
}
