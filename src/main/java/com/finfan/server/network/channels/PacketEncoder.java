package com.finfan.server.network.channels;

import com.finfan.server.network.packets.dto.outcoming.AbstractOutcomePacket;
import com.finfan.server.network.packets.serder.PacketSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@Sharable
@RequiredArgsConstructor
public class PacketEncoder extends MessageToByteEncoder<AbstractOutcomePacket> {

    private final Map<Class<?>, PacketSerializer> serializers;

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractOutcomePacket msg, ByteBuf out) throws Exception {
        PacketSerializer packetSerializer = serializers.get(msg.getClass());
        if (packetSerializer == null) {
            throw new RuntimeException("Сериализатор не найден: " + msg.getPacketId());
        }

        packetSerializer.serialize(msg, out);
        log.debug("[→] Outcome ID[{}] DATA[{}]", msg.getPacketId(), msg);
    }
}
