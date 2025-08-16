package com.finfan.server.service;

import com.finfan.server.entity.AccountEntity;
import com.finfan.server.enums.Permission;
import com.finfan.server.enums.ReceiveLoginResponse;
import com.finfan.server.network.GameSession;
import com.finfan.server.network.packets.dto.incoming.RequestLogin;
import com.finfan.server.network.packets.dto.outcoming.ResponseLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AccountService accountService;
    private final LobbyService lobbyService;

    public void tryConnect(GameSession gameSession, String accountName, String password) {
        AccountEntity account = accountService.getAccount(accountName);
        ResponseLogin responseLogin = new ResponseLogin();
        if (account == null) {
            responseLogin.setResponse(ReceiveLoginResponse.INCORRECT_ACCOUNT_NAME_OR_PASSWORD);
        } else {
            if (account.getPermission() == Permission.BANNED) {
                responseLogin.setResponse(ReceiveLoginResponse.BANNED);
            } else {
                String inputPassword = accountService.createSalt(accountName, password);
                String serverPassword = account.getPassword();
                if (!inputPassword.equals(serverPassword)) {
                    responseLogin.setResponse(ReceiveLoginResponse.INCORRECT_ACCOUNT_NAME_OR_PASSWORD);
                } else {
                    accountService.updateOnline(account.getId(), true);
                    responseLogin.setResponse(ReceiveLoginResponse.OK);
                    responseLogin.setId(account.getId());
                }
            }
        }

        gameSession.sendPacket(responseLogin);
    }

    @EventListener
    public void onRequestLogin(RequestLogin requestLogin) {
        tryConnect(requestLogin.getGameSession(), requestLogin.getAccount(), requestLogin.getPassword());
    }

}
