package com.finfan.server.network.packets.serder.deserializer;

import com.finfan.server.enums.DuelCategory;
import com.finfan.server.enums.DuelOpponent;
import com.finfan.server.network.packets.dto.incoming.RequestDuelRegister;
import com.finfan.server.network.packets.serder.PacketDeserializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

@Component
public class RequestDuelRegisterDeserializer implements PacketDeserializer {

    @Override
    public RequestDuelRegister deserialize(ChannelHandlerContext ctx, ByteBuf buffer) {
        RequestDuelRegister in = new RequestDuelRegister();
        in.setDuelOpponent(DuelOpponent.values()[buffer.readByte()]);
        in.setDuelCategory(DuelCategory.values()[buffer.readByte()]);
        in.setRegisterTime(buffer.readLong());
        return in;
    }

    @Override
    public int getPacketId() {
        return RequestDuelRegister.PACKET_ID;
    }
}
