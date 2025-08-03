package com.finfan.server.service;

import com.finfan.server.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AccountService accountService;

    private Map<String, AccountEntity> connections = new ConcurrentHashMap<>();

    private boolean validatePassword(AccountEntity account, String password) {
        String clientPassword = accountService.createSalt(account.getName(), password);
        String serverPassword = account.getPassword();
        return serverPassword.equals(clientPassword);
    }

    public boolean isOnline(String accountName) {
        return connections.containsKey(accountName);
    }

    public void disconnect(String accountName) {
        //TODO: racefull disconenct with packet send
        connections.remove(accountName);
    }

}
