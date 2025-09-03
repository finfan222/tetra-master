package com.finfan.server.network.packets.serder.deserializer;

import com.finfan.server.network.packets.dto.incoming.RequestCardShop;
import com.finfan.server.network.packets.serder.PacketDeserializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

@Component
public class RequestCardShopDeserializer implements PacketDeserializer {

    @Override
    public RequestCardShop deserialize(ChannelHandlerContext ctx, ByteBuf buffer) {
        return new RequestCardShop();
    }

    @Override
    public int getPacketId() {
        return RequestCardShop.PACKET_ID;
    }

}
