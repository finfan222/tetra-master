package com.finfan.server.service;

import com.finfan.server.entity.AccountEntity;
import com.finfan.server.enums.Permission;
import com.finfan.server.enums.responses.EResponseLogin;
import com.finfan.server.network.GameSession;
import com.finfan.server.network.packets.dto.incoming.RequestLogin;
import com.finfan.server.network.packets.dto.outcoming.ResponseLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final AccountService accountService;
    private final LobbyService lobbyService;

    public void tryConnect(GameSession gameSession, String accountName, String password) {
        AccountEntity account = accountService.getAccount(accountName);
        ResponseLogin responseLogin = new ResponseLogin();
        if (account == null) {
            responseLogin.setResponse(EResponseLogin.INCORRECT_ACCOUNT_NAME_OR_PASSWORD);
        } else {
            if (account.getPermission() == Permission.BANNED) {
                responseLogin.setResponse(EResponseLogin.BANNED);
            } else {
                String inputPassword = accountService.createSalt(accountName, password);
                String serverPassword = account.getPassword();
                if (!inputPassword.equals(serverPassword)) {
                    responseLogin.setResponse(EResponseLogin.INCORRECT_ACCOUNT_NAME_OR_PASSWORD);
                } else {
                    if (Objects.equals(account.getOnline(), true)) {
                        responseLogin.setResponse(EResponseLogin.ALREADY_IN_USE); //TODO: обработать внутри клиента
                        log.warn("Попытка зайти на аккаунт {} который уже находится в использовании. Нужен фикс на выбивание клиента?", accountName);
                    } else {
                        responseLogin.setResponse(EResponseLogin.OK);
                        responseLogin.setId(account.getId());
                        gameSession.setAccount(account);
                        accountService.updateOnline(account, true);
                        lobbyService.sendOnline();
                    }
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
