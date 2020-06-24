package com.instachecker.services;

import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
public interface ConnectionService {
    WebDriver getWebDriver();
}
