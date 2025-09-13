package com.finfan.server.network;

import com.finfan.server.entity.AccountEntity;
import com.finfan.server.events.network.GameSessionActive;
import com.finfan.server.events.network.GameSessionInactive;
import com.finfan.server.network.packets.dto.incoming.AbstractIncomePacket;
import com.finfan.server.network.packets.dto.outcoming.AbstractOutcomePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@RequiredArgsConstructor
public class GameSession extends SimpleChannelInboundHandler<AbstractIncomePacket> {

    private final ApplicationEventPublisher applicationEventPublisher;
    private Channel channel;
    @EqualsAndHashCode.Exclude
    private AccountEntity account;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
        applicationEventPublisher.publishEvent(new GameSessionActive(this));
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage());
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
