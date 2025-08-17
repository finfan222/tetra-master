package com.finfan.server.network.packets.serder;

import com.finfan.server.network.packets.dto.outcoming.AbstractOutcomePacket;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public interface PacketSerializer<T extends AbstractOutcomePacket> {

    void serialize(T data, ByteBuf buffer);

    default void writeString(ByteBuf buffer, String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);
    }

}
