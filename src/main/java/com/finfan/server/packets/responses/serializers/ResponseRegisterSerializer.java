package com.finfan.server.packets.responses.serializers;

import com.finfan.server.PacketSerializer;
import com.finfan.server.packets.PacketData;
import com.finfan.server.packets.responses.ReceiveRegister;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

public class ResponseRegisterSerializer implements PacketSerializer {

    @Override
    public ByteBuf serialize(PacketData data) {
        if (!(data instanceof ReceiveRegister packet)) {
            return null;
        }

        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(packet.getPacketId());
        buffer.writeInt(packet.getPacketId());
        buffer.writeInt(packet.getResponse().ordinal());
        return buffer;
    }

    @Override
    public int getPacketId() {
        return ReceiveRegister.PACKET_ID;
    }

}
