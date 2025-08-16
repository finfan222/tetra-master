package com.finfan.server.network;

import com.finfan.server.events.network.GameSessionActive;
import com.finfan.server.events.network.GameSessionInactive;
import com.finfan.server.network.packets.dto.incoming.AbstractIncomePacket;
import com.finfan.server.network.packets.dto.outcoming.AbstractOutcomePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
public class GameSession extends SimpleChannelInboundHandler<AbstractIncomePacket> {

    private final ApplicationEventPublisher applicationEventPublisher;
    private Channel channel;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
        applicationEventPublisher.publishEvent(new GameSessionActive(this));
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //TODO
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        applicationEventPublisher.publishEvent(new GameSessionInactive(this));
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractIncomePacket msg) throws Exception {
        msg.setGameSession(this);
        applicationEventPublisher.publishEvent(msg);
    }

    public void sendPacket(AbstractOutcomePacket packet) {
        channel.writeAndFlush(packet);
    }

}
