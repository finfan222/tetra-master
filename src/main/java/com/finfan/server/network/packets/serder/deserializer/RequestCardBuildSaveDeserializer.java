package com.finfan.server.network.packets.serder.deserializer;

import com.finfan.server.entity.dto.CardDto;
import com.finfan.server.enums.DeckBuild;
import com.finfan.server.network.packets.dto.incoming.RequestCardBuildSave;
import com.finfan.server.network.packets.serder.PacketDeserializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequestCardBuildSaveDeserializer implements PacketDeserializer {

    @Override
    public RequestCardBuildSave deserialize(ChannelHandlerContext ctx, ByteBuf buffer) {
        RequestCardBuildSave in = new RequestCardBuildSave();
        in.setBuild(buffer.readByte());
        int size = buffer.readInt();
        List<CardDto> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            CardDto dto = new CardDto();
            dto.setId(buffer.readLong());
            dto.setBuild(DeckBuild.values()[buffer.readByte()]);
            list.add(dto);
        }
        in.setCards(list);
        return in;
    }

    @Override
    public int getPacketId() {
        return RequestCardBuildSave.PACKET_ID;
    }
}
