package com.finfan.server.network.packets.serder.deserializer;

import com.finfan.server.network.packets.dto.incoming.RequestPlayerList;
import com.finfan.server.network.packets.serder.PacketDeserializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

@Component
public class RequestPlayerListDeserializer implements PacketDeserializer {

    @Override
    public RequestPlayerList deserialize(ChannelHandlerContext ctx, ByteBuf buffer) {
        RequestPlayerList in = new RequestPlayerList();
        in.setListPage(buffer.readInt());
        return in;
    }

    @Override
    public int getPacketId() {
        return RequestPlayerList.PACKET_ID;
    }
}
