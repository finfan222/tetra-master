package com.finfan.server.channels;


import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PacketHandlerFactory {

    private final ApplicationEventPublisher applicationEventPublisher;

    public PacketHandler create() {
        return new PacketHandler(applicationEventPublisher);
    }

}
