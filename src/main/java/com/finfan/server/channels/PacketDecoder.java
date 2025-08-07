package com.finfan.server.channels;

import com.finfan.server.PacketDeserializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Sharable
@RequiredArgsConstructor
public class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {

    private final Map<Integer, PacketDeserializer> deserializers;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        int packetId = buffer.readInt();
        System.out.println("Decode: " + packetId);
        Object packet = deserializers.get(packetId).deserialize(ctx, buffer);// кейсы с обработками пакет не найден и т.д..
        out.add(packet); // передаем дальше по пайплайну
    }

}