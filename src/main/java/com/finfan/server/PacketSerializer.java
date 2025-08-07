package com.finfan.server;

import com.finfan.server.packets.PacketData;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public interface PacketSerializer {

    void serialize(PacketData data, ByteBuf buffer);

    int getPacketId();

    default void writeString(ByteBuf buffer, String value) {
        buffer.writeInt(value.length());
        buffer.writeBytes(value.getBytes(StandardCharsets.UTF_8));
    }

}
