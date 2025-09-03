package com.finfan.server.network.packets.serder.serializer;

import com.finfan.server.network.packets.dto.outcoming.ResponseCardShopInfo;
import com.finfan.server.network.packets.serder.PacketSerializer;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public class ResponseCardShopInfoSerializer implements PacketSerializer<ResponseCardShopInfo> {

    @Override
    public void serialize(ResponseCardShopInfo data, ByteBuf out) {
        out.writeInt(data.getPacketId());
        out.writeLong(data.getMyGil());
        out.writeBoolean(data.isCardInStock());
        out.writeInt(data.getCardCount());
        out.writeBoolean(data.isCardIsUnique());
    }

}
