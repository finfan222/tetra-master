package com.finfan.server.network;

import com.finfan.server.events.network.GameSessionActive;
import com.finfan.server.events.network.GameSessionInactive;
import com.finfan.server.network.packets.dto.outcoming.AbstractOutcomePacket;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class GameSessionRegistry {

    private static final Object VALUE = new Object();

    private final Map<GameSession, Object> gameSessions = new ConcurrentHashMap<>();

    @EventListener
    protected void onGameSessionActive(GameSessionActive event) {
        gameSessions.put(event.getGameSession(), VALUE);
    }

    @EventListener
    protected void onGameSessionInactive(GameSessionInactive event) {
        gameSessions.remove(event.getGameSession());
    }

    public List<GameSession> getGameSessions() {
        return new ArrayList<>(gameSessions.keySet());
    }

    public int onlineCount() {
        return gameSessions.size();
    }

    public void multicast(AbstractOutcomePacket response) {
        gameSessions.keySet().forEach(e -> e.sendPacket(response));
    }
}
