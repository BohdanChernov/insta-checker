package com.instachecker.services.operations;

import com.instachecker.services.ConnectionService;
import com.instachecker.services.ParseAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperationServiceImpl implements OperationService {
    @Autowired
    ParseAccountService parseAccountService;
    @Autowired
    ConnectionService connectionService;
    List<String> followers;
    List<String> following;
    List<String> unsubscribedUsers = new ArrayList<>();

    public void retrieveData() {
        setFollowers();
        setFollowings();
    }

    public void setFollowers() {
        followers = parseAccountService.getFollowers();
    }

    public void setFollowings() {
        following = parseAccountService.getFollowing();
    }

    @Override
    public List<String> getUnsubscribedUsers() {
        retrieveData();
        for (String user : following) {
            if (!followers.contains(user)) {
                unsubscribedUsers.add(user);
            }
        }
        return unsubscribedUsers;
    }
}
