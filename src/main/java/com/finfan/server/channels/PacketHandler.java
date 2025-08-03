package com.finfan.server.channels;

import com.finfan.server.packets.PacketData;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@Sharable
@RequiredArgsConstructor
public class PacketHandler extends SimpleChannelInboundHandler<PacketData> {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketData msg) throws Exception {
        applicationEventPublisher.publishEvent(msg);
        ctx.writeAndFlush(msg);
    }

}
