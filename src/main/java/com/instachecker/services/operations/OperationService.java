package com.instachecker.services.operations;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OperationService {
    List<String> getUnsubscribedUsers();
}
