package com.finfan.server.network.packets.serder.deserializer;

import com.finfan.server.network.packets.dto.incoming.RequestOnline;
import com.finfan.server.network.packets.serder.PacketDeserializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

@Component
public class RequestOnlineDeserializer implements PacketDeserializer {

    @Override
    public RequestOnline deserialize(ChannelHandlerContext ctx, ByteBuf buffer) {
        return new RequestOnline();
    }

    @Override
    public int getPacketId() {
        return RequestOnline.PACKET_ID;
    }

}
