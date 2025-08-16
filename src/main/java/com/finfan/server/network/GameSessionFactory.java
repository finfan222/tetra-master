package com.finfan.server.network;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameSessionFactory {

    private final ApplicationEventPublisher applicationEventPublisher;

    public GameSession create() {
        return new GameSession(applicationEventPublisher);
    }

}
