package com.instachecker.services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    private WebDriver webDriver;
    private ChromeOptions options;
    @Value("${web.driver.path}")
    private String webDriverPath;
    @Autowired
    IntermediateService intermediateService;

    @Value("${inttagram.link}")
    private String intagramLink;
    @Value("${instagram.login}")
    private String login;
    @Value("${instagram.password}")
    private String password;

    public void connect() {
        setUpConnection();
        putLoginAndPassword();
    }

    public void setUpConnection() {
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--popup-blocking");
        options.addArguments("--disable-infobars");
        webDriver = new ChromeDriver(options);
        webDriver.get(intagramLink);
    }

    public void putLoginAndPassword() {
        intermediateService.waitTwoSec();
        List<WebElement> inputs = webDriver.findElements(By.tagName("input"));
        inputs.get(0).sendKeys(login);
        inputs.get(1).sendKeys(password);
        pushLogin();
        intermediateService.waitTwoSec();
    }

    public void pushLogin() {
        List<WebElement> buttons = webDriver.findElements(By.tagName("button"));
        buttons.get(1).click();
    }

    public WebDriver getWebDriver() {
        connect();
        return webDriver;
    }
}
