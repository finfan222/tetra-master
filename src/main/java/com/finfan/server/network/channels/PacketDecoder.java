package com.finfan.server.network.channels;

import com.finfan.server.network.packets.serder.PacketDeserializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Sharable
@RequiredArgsConstructor
public class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {

    private final Map<Integer, PacketDeserializer> deserializers;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        int packetId = buffer.readInt();
        PacketDeserializer packetDeserializer = deserializers.get(packetId);
        if (packetDeserializer == null) {
            throw new RuntimeException("Десериализатор не найден для: " + packetId);
        }

        log.debug("[→] Income: ID[{}]({}) LEN[{}]", packetId, packetDeserializer.getClass().getSimpleName(), buffer.readableBytes());
        out.add(packetDeserializer.deserialize(ctx, buffer));
    }

}