package com.finfan.server.service;

import com.finfan.server.entity.AccountEntity;
import com.finfan.server.entity.ProfileEntity;
import com.finfan.server.events.network.GameSessionInactive;
import com.finfan.server.network.GameSession;
import com.finfan.server.network.GameSessionRegistry;
import com.finfan.server.network.packets.dto.incoming.RequestPlayerInfo;
import com.finfan.server.network.packets.dto.incoming.RequestPlayerList;
import com.finfan.server.network.packets.dto.outcoming.ResponsePlayerInfo;
import com.finfan.server.network.packets.dto.outcoming.ResponsePlayerList;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LobbyService {

    private final GameSessionRegistry gameSessionRegistry;
    private final AccountService accountService;
    private final ProfileService profileService;

    public void sendPlayerList() {
        updatePlayerList(0);
    }

    public void updatePlayerList(int page) {
        ResponsePlayerList responsePlayerList = new ResponsePlayerList();
        responsePlayerList.setProfiles(profileService.getProfiles(true, page, 50));
        gameSessionRegistry.multicast(responsePlayerList);
    }

    @EventListener
    public void onRequestPlayerList(RequestPlayerList requestPlayerList) {
        updatePlayerList(requestPlayerList.getListPage());
    }

    public void updatePlayerInfo(long playerId, GameSession gameSession) {
        AccountEntity account = accountService.getAccount(playerId);
        if (account != null) {
            ProfileEntity profile = account.getProfile();
            ResponsePlayerInfo responsePlayerInfo = new ResponsePlayerInfo();
            responsePlayerInfo.setId(playerId);
            responsePlayerInfo.setName(account.getName());
            responsePlayerInfo.setRating(profile.getRating());
            responsePlayerInfo.setGil(profile.getGil());
            responsePlayerInfo.setWin(profile.getWins());
            responsePlayerInfo.setLoss(profile.getLosses());
            responsePlayerInfo.setPortrait(profile.getPortrait());
            gameSession.sendPacket(responsePlayerInfo);
        }
    }

    @EventListener
    public void onUpdatePlayerInfo(RequestPlayerInfo requestPlayerInfo) {
        updatePlayerInfo(requestPlayerInfo.getPlayerId(), requestPlayerInfo.getGameSession());
    }

    @EventListener
    protected void onGameSessionInactive(GameSessionInactive event) {
        sendPlayerList();
    }
}
