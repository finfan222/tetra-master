package com.finfan.server.network.packets.serder;

import com.finfan.server.network.packets.dto.incoming.AbstractIncomePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

public interface PacketDeserializer {

    AbstractIncomePacket deserialize(ChannelHandlerContext ctx, ByteBuf buffer);

    int getPacketId();

    default String readString(ByteBuf buffer) {
        int length = buffer.readInt();
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
