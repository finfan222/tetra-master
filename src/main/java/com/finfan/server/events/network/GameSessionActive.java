package com.finfan.server.events.network;

import com.finfan.server.network.GameSession;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GameSessionActive {
    private final GameSession gameSession;
}
