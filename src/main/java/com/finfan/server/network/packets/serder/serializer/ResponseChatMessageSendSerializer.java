package com.finfan.server.network.packets.serder.serializer;

import com.finfan.server.network.packets.dto.outcoming.ResponseChatMessageSend;
import com.finfan.server.network.packets.serder.PacketSerializer;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public class ResponseChatMessageSendSerializer implements PacketSerializer<ResponseChatMessageSend> {

    @Override
    public void serialize(ResponseChatMessageSend data, ByteBuf buffer) {
        buffer.writeInt(data.getPacketId());
        writeString(buffer, data.getPlayerName());
        writeString(buffer, data.getPlayerMessage());
        buffer.writeInt(data.getColor().ordinal());
    }

}
