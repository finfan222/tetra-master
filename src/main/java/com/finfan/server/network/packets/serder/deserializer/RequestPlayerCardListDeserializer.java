package com.finfan.server.network.packets.serder.deserializer;

import com.finfan.server.network.packets.dto.incoming.RequestPlayerCardList;
import com.finfan.server.network.packets.serder.PacketDeserializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

@Component
public class RequestPlayerCardListDeserializer implements PacketDeserializer {

    @Override
    public RequestPlayerCardList deserialize(ChannelHandlerContext ctx, ByteBuf buffer) {
        return new RequestPlayerCardList();
    }

    @Override
    public int getPacketId() {
        return RequestPlayerCardList.PACKET_ID;
    }
}
