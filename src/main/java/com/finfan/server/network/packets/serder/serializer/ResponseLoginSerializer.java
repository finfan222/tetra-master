package com.finfan.server.network.packets.serder.serializer;

import com.finfan.server.network.packets.dto.outcoming.ResponseLogin;
import com.finfan.server.network.packets.serder.PacketSerializer;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public class ResponseLoginSerializer implements PacketSerializer<ResponseLogin> {

    @Override
    public void serialize(ResponseLogin data, ByteBuf out) {
        out.writeInt(data.getPacketId());
        out.writeInt(data.getResponse().ordinal());
        out.writeLong(data.getId());
    }

}
