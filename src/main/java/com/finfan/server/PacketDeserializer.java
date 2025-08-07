package com.finfan.server;

import com.finfan.server.packets.PacketData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

public interface PacketDeserializer<Response extends PacketData> {

    <T extends PacketData> T deserialize(ChannelHandlerContext ctx, ByteBuf buffer);

    void serializeToResponse(Response data, ByteBuf out);

    int getPacketId();

    default String readString(ByteBuf buffer) {
        int length = buffer.readInt();
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    default void writeString(ByteBuf buffer, String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);
    }
}
