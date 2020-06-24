package com.instachecker.controllers;

import com.instachecker.services.ParseAccountService;
import com.instachecker.services.operations.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ConnectionController {
    @Autowired
    ParseAccountService parseAccountService;
    @Autowired
    OperationService operationService;

    @GetMapping("/putAccountLink")
    @ResponseBody
    public List<String> connectToAccount(@RequestParam String accountName) {
        parseAccountService.parseAccount(accountName);
        List<String> unsubscribedUsers = new ArrayList<>();
        unsubscribedUsers = operationService.getUnsubscribedUsers();
        return unsubscribedUsers;
    }
}