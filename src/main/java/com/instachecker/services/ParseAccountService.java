package com.instachecker.services;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParseAccountService {
    void parseAccount(String url);

    List<String> getFollowers();

    List<String> getFollowing();
}
