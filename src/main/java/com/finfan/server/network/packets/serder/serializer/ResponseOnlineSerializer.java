package com.finfan.server.network.packets.serder.serializer;

import com.finfan.server.network.packets.dto.outcoming.ResponseOnline;
import com.finfan.server.network.packets.serder.PacketSerializer;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public class ResponseOnlineSerializer implements PacketSerializer<ResponseOnline> {

    @Override
    public void serialize(ResponseOnline data, ByteBuf out) {
        out.writeInt(data.getPacketId());
        out.writeInt(data.getPlayerCount());
    }

}
