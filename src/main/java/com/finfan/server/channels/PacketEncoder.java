package com.finfan.server.channels;

import com.finfan.server.PacketSerializer;
import com.finfan.server.packets.PacketData;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Sharable
@RequiredArgsConstructor
public class PacketEncoder extends MessageToMessageEncoder<PacketData> {

    private final Map<Integer, PacketSerializer> serializers;

    @Override
    protected void encode(ChannelHandlerContext ctx, PacketData msg, List<Object> out) throws Exception {
        System.out.println("ASDASDSAD");
        System.out.println(msg);
    }
}
