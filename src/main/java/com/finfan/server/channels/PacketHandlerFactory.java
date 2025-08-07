package com.finfan.server.channels;


import com.finfan.server.PacketDeserializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PacketHandlerFactory {

    private final Map<Integer, PacketDeserializer> deserializers;

    public PacketHandler create() {
        return new PacketHandler(deserializers);
    }

}
