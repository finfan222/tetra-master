package com.finfan.server.network.packets.serder.deserializer;

import com.finfan.server.network.packets.dto.incoming.RequestCardShopInfo;
import com.finfan.server.network.packets.serder.PacketDeserializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

@Component
public class RequestCardShopInfoDeserializer implements PacketDeserializer {

    @Override
    public RequestCardShopInfo deserialize(ChannelHandlerContext ctx, ByteBuf buffer) {
        RequestCardShopInfo in = new RequestCardShopInfo();
        in.setCardId(buffer.readLong());
        return in;
    }

    @Override
    public int getPacketId() {
        return RequestCardShopInfo.PACKET_ID;
    }

}
