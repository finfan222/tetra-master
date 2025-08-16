package com.finfan.server.network.packets.serder.deserializer;

import com.finfan.server.network.packets.dto.incoming.RequestLogin;
import com.finfan.server.network.packets.serder.PacketDeserializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RequestLoginDeserializer implements PacketDeserializer {

    @Override
    public RequestLogin deserialize(ChannelHandlerContext ctx, ByteBuf buffer) {
        RequestLogin in = new RequestLogin();
        in.setAccount(readString(buffer));
        in.setPassword(readString(buffer));
        in.setIpAddress(readString(buffer));
        in.setLastAccess(LocalDateTime.parse(readString(buffer)));
        return in;
    }

    @Override
    public int getPacketId() {
        return RequestLogin.PACKET_ID;
    }

}
