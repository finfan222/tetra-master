package com.finfan.server.network.packets.serder.serializer;

import com.finfan.server.entity.dto.CardDto;
import com.finfan.server.network.packets.dto.outcoming.ResponseCardBuildSave;
import com.finfan.server.network.packets.serder.PacketSerializer;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public class ResponseCardBuildSaveSerializer implements PacketSerializer<ResponseCardBuildSave> {

    @Override
    public void serialize(ResponseCardBuildSave data, ByteBuf out) {
        out.writeInt(data.getPacketId());
        out.writeInt(data.getResponse().ordinal());
        if (data.getModifiedCards() == null) {
            out.writeInt(0x00);
        } else {
            out.writeInt(data.getModifiedCards().size());
            for (CardDto card : data.getModifiedCards()) {
                out.writeLong(card.getCardId());
                out.writeLong(card.getId());
                out.writeByte(card.getBuild() != null ? card.getBuild().ordinal() : -1);
            }
        }
    }

}
