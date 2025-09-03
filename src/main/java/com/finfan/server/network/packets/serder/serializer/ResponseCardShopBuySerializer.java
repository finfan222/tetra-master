package com.finfan.server.network.packets.serder.serializer;

import com.finfan.server.network.packets.dto.outcoming.ResponseCardShopBuy;
import com.finfan.server.network.packets.serder.PacketSerializer;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public class ResponseCardShopBuySerializer implements PacketSerializer<ResponseCardShopBuy> {

    @Override
    public void serialize(ResponseCardShopBuy data, ByteBuf out) {
        out.writeInt(data.getPacketId());
        out.writeInt(data.getResponseMessage().ordinal());
        if (data.getCard() != null) {
            out.writeLong(data.getCard().getTemplate().getId());
            out.writeInt(data.getCard().getAtkArrows());
            out.writeInt(data.getCard().getAtk());
            out.writeInt(data.getCard().getAtkType().ordinal());
            out.writeInt(data.getCard().getPDef());
            out.writeInt(data.getCard().getMDef());
            out.writeFloat(data.getCard().getRateLvlAtk());
            out.writeFloat(data.getCard().getRateLvlPDef());
            out.writeFloat(data.getCard().getRateLvlMDef());
            out.writeFloat(data.getCard().getRateLvlAtkTypeToA());
            out.writeFloat(data.getCard().getRateLvlAtkTypeToX());
        } else {
            out.writeLong(0x00); // empty template id
        }
    }

}
