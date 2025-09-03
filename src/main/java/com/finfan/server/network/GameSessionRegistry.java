package com.finfan.server.network;

import com.finfan.server.events.network.GameSessionActive;
import com.finfan.server.events.network.GameSessionInactive;
import com.finfan.server.network.packets.dto.outcoming.AbstractOutcomePacket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameSessionRegistry {

    private static final Object VALUE = new Object();

    private final Map<GameSession, Object> gameSessions = new ConcurrentHashMap<>();

    @EventListener
    protected void onGameSessionActive(GameSessionActive event) {
        gameSessions.put(event.getGameSession(), VALUE);
        log.debug("Клиент подключён: {} (total: {})", event.getGameSession().getChannel().id(), onlineCount());
    }

    @EventListener
    protected void onGameSessionInactive(GameSessionInactive event) {
        gameSessions.remove(event.getGameSession());
        log.debug("Клиент отключён: {} (total: {})", event.getGameSession().getChannel().id(), onlineCount());
    }

    public List<GameSession> getGameSessions() {
        return new ArrayList<>(gameSessions.keySet());
    }

    public int onlineCount() {
        return gameSessions.keySet().stream()
                .filter(g -> g.getAccount() != null && Objects.equals(g.getAccount().getOnline(), true))
                .toList()
                .size();
    }

    public void multicast(AbstractOutcomePacket response) {
        gameSessions.keySet().forEach(e -> e.sendPacket(response));
    }
}
