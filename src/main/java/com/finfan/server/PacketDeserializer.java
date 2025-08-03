package com.finfan.server;

import com.finfan.server.packets.PacketData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

public interface PacketDeserializer {

    <T extends PacketData> T deserialize(ChannelHandlerContext ctx, ByteBuf buffer);

    int getPacketId();

    default String readString(ByteBuf buffer) {
        int length = buffer.readInt();
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
