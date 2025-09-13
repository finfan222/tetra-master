package com.finfan.server.service;

import com.finfan.server.entity.AccountEntity;
import com.finfan.server.entity.ProfileEntity;
import com.finfan.server.network.GameSessionRegistry;
import com.finfan.server.network.packets.dto.incoming.RequestOnline;
import com.finfan.server.network.packets.dto.incoming.RequestPlayerInfo;
import com.finfan.server.network.packets.dto.incoming.RequestPlayerList;
import com.finfan.server.network.packets.dto.outcoming.ResponseOnline;
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
    protected void onRequestPlayerList(RequestPlayerList requestPlayerList) {
        updatePlayerList(requestPlayerList.getListPage());
    }

    public void sendOnline() {
        updateOnline();
    }

    public void updateOnline() {
        ResponseOnline responseOnline = new ResponseOnline();
        responseOnline.setPlayerCount(gameSessionRegistry.onlineCount());
        gameSessionRegistry.multicast(responseOnline);
    }

    @EventListener
    protected void onUpdatePlayerInfo(RequestPlayerInfo event) {
        AccountEntity account = event.getGameSession().getAccount();
        ProfileEntity profile = account.getProfile();
        ResponsePlayerInfo responsePlayerInfo = new ResponsePlayerInfo();
        responsePlayerInfo.setId(account.getId());
        responsePlayerInfo.setName(account.getName());
        responsePlayerInfo.setRating(profile.getRating());
        responsePlayerInfo.setGil(profile.getGil());
        responsePlayerInfo.setWin(profile.getWins());
        responsePlayerInfo.setLoss(profile.getLosses());
        responsePlayerInfo.setPortrait(profile.getPortrait());
        event.getGameSession().sendPacket(responsePlayerInfo);
    }

    @EventListener
    protected void onRequestOnline(RequestOnline event) {
        updateOnline();
    }
}
