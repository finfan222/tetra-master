package com.finfan.server.network.packets.serder.deserializer;

import com.finfan.server.network.packets.dto.incoming.RequestCardShopBuy;
import com.finfan.server.network.packets.serder.PacketDeserializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

@Component
public class RequestCardShopBuyDeserializer implements PacketDeserializer {

    @Override
    public RequestCardShopBuy deserialize(ChannelHandlerContext ctx, ByteBuf buffer) {
        RequestCardShopBuy in = new RequestCardShopBuy();
        in.setShopCardId(buffer.readLong());
        return in;
    }

    @Override
    public int getPacketId() {
        return RequestCardShopBuy.PACKET_ID;
    }

}
