package com.finfan.server.channels;

import com.finfan.server.PacketDeserializer;
import com.finfan.server.packets.PacketData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Sharable
@RequiredArgsConstructor
public class PacketEncoder extends MessageToByteEncoder<PacketData> {

    private final Map<Integer, PacketDeserializer> deserializers;

    @Override
    protected void encode(ChannelHandlerContext ctx, PacketData msg, ByteBuf out) throws Exception {
        System.out.println("Encode: " + msg.getPacketId());
        deserializers.get(msg.getRequestPacketId()).serializeToResponse(msg, out);
    }
}
