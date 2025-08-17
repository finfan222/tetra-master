package com.finfan.server.network.packets.serder.deserializer;

import com.finfan.server.network.packets.dto.incoming.RequestChatMessageSend;
import com.finfan.server.network.packets.serder.PacketDeserializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

@Component
public class RequestChatMessageSendDeserializer implements PacketDeserializer {

    @Override
    public RequestChatMessageSend deserialize(ChannelHandlerContext ctx, ByteBuf buffer) {
        RequestChatMessageSend in = new RequestChatMessageSend();
        in.setPlayerId(buffer.readLong());
        in.setPlayerMessage(readString(buffer));
        return in;
    }

    @Override
    public int getPacketId() {
        return RequestChatMessageSend.PACKET_ID;
    }
}
