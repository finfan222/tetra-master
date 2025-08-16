package com.finfan.server.network.packets.serder.serializer;

import com.finfan.server.network.packets.dto.outcoming.ResponsePlayerInfo;
import com.finfan.server.network.packets.serder.PacketSerializer;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public class ResponsePlayerInfoSerializer implements PacketSerializer<ResponsePlayerInfo> {

    @Override
    public void serialize(ResponsePlayerInfo data, ByteBuf buffer) {
        buffer.writeInt(data.getPacketId());
        buffer.writeLong(data.getId());
        writeString(buffer, data.getName());
        buffer.writeInt(data.getRating());
        buffer.writeLong(data.getGil());
        buffer.writeInt(data.getWin());
        buffer.writeInt(data.getLoss());
    }
}
