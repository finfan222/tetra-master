package com.finfan.server.network.packets.serder.deserializer;

import com.finfan.server.network.packets.dto.incoming.RequestPlayerInfo;
import com.finfan.server.network.packets.serder.PacketDeserializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

@Component
public class RequestPlayerInfoDeserializer implements PacketDeserializer {

    @Override
    public RequestPlayerInfo deserialize(ChannelHandlerContext ctx, ByteBuf buffer) {
        RequestPlayerInfo in = new RequestPlayerInfo();
        in.setPlayerId(buffer.readLong());
        return in;
    }

    @Override
    public int getPacketId() {
        return RequestPlayerInfo.PACKET_ID;
    }
}
