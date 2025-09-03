package com.finfan.server.network.packets.serder.serializer;

import com.finfan.server.entity.CardShopEntity;
import com.finfan.server.network.packets.dto.outcoming.ResponseCardShop;
import com.finfan.server.network.packets.serder.PacketSerializer;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public class ResponseCardShopSerializer implements PacketSerializer<ResponseCardShop> {

    @Override
    public void serialize(ResponseCardShop data, ByteBuf out) {
        out.writeInt(data.getPacketId());
        writeString(out, data.getNextUpdateDate().toString());
        out.writeInt(data.getShopCards().size());
        for (CardShopEntity temp : data.getShopCards()) {
            out.writeLong(temp.getTemplate().getId());
            out.writeInt(temp.getAtkArrows());
            out.writeLong(temp.getId());
            out.writeInt(temp.getPrice());
        }
    }

}
