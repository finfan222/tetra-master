package com.finfan.server.packets.requests.deserializers;

import com.finfan.server.PacketDeserializer;
import com.finfan.server.packets.requests.RequestRegister;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RequestRegisterDeserializer implements PacketDeserializer {

    @SuppressWarnings("unchecked")
    public RequestRegister deserialize(ChannelHandlerContext ctx, ByteBuf buffer) {
        RequestRegister in = new RequestRegister();
        in.setName(readString(buffer));
        in.setPassword(readString(buffer));
        in.setIpAddress(readString(buffer));
        in.setPortrait(buffer.readInt());
        in.setTalentId(buffer.readInt());

        // Чтение Dictionary<int, int>
        int dictSize = buffer.readInt();
        Map<Long, Integer> cards = new HashMap<>();
        for (int i = 0; i < dictSize; i++) {
            cards.put(buffer.readLong(), buffer.readInt());
        }
        in.setBaseCards(cards);
        return in;
    }

    @Override
    public int getPacketId() {
        return RequestRegister.PACKET_ID;
    }

}
