package com.instachecker.services;

import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParseFollowings {
    List<String> getFollowingsFromTab(WebDriver webDriver, String intagramUrlAccountToParse);
}
