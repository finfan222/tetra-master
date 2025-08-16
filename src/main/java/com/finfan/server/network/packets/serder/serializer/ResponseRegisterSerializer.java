package com.finfan.server.network.packets.serder.serializer;

import com.finfan.server.network.packets.dto.outcoming.ResponseRegister;
import com.finfan.server.network.packets.serder.PacketSerializer;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public class ResponseRegisterSerializer implements PacketSerializer<ResponseRegister> {

    @Override
    public void serialize(ResponseRegister data, ByteBuf out) {
        out.writeInt(data.getPacketId());
        out.writeInt(data.getResponse().ordinal());
    }

}
