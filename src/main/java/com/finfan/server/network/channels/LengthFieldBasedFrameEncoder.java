package com.finfan.server.network.channels;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author m0nster
 */
@Slf4j
@Sharable
@RequiredArgsConstructor
public class LengthFieldBasedFrameEncoder extends MessageToMessageEncoder<ByteBuf> {

    private final int maxLength;
    private final int headerLength;

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer(headerLength, maxLength);
        //Добавить 4 байта, длина пакета
        int packetId = msg.readInt();
        msg.resetReaderIndex();
        buffer.writeInt(msg.readableBytes() + headerLength); // + headerLength
        buffer.writeBytes(msg);
        out.add(buffer);
        log.debug("[←] Outcome encode: ID[{}] LEN[{}]", packetId, buffer.writerIndex());
    }

}
