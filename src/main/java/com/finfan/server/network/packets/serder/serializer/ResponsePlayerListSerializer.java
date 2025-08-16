package com.finfan.server.network.packets.serder.serializer;

import com.finfan.server.entity.ProfileEntity;
import com.finfan.server.network.packets.dto.outcoming.ResponsePlayerList;
import com.finfan.server.network.packets.serder.PacketSerializer;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

@Component
public class ResponsePlayerListSerializer implements PacketSerializer<ResponsePlayerList> {

    @Override
    public void serialize(ResponsePlayerList data, ByteBuf out) {
        out.writeInt(data.getPacketId());
        out.writeInt(data.getProfiles().size());
        for (ProfileEntity profile : data.getProfiles()) {
            out.writeLong(profile.getId());
            writeString(out, profile.getAccount().getName());
            out.writeInt(profile.getRating());
            out.writeInt(profile.getStatus().ordinal());
        }
    }

}
