package com.finfan.server.channels;

import com.finfan.server.PacketDeserializer;
import com.finfan.server.packets.PacketData;
import com.finfan.server.packets.handlers.RequestLoginHandler;
import com.finfan.server.packets.handlers.RequestRegisterHandler;
import com.finfan.server.packets.requests.RequestLogin;
import com.finfan.server.packets.requests.RequestRegister;
import com.finfan.server.packets.responses.ResponseLogin;
import com.finfan.server.packets.responses.ResponseRegister;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Sharable
@RequiredArgsConstructor
public class PacketHandler extends SimpleChannelInboundHandler<PacketData> {

    private final Map<Integer, PacketDeserializer> deserializers;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketData msg) throws Exception {
        System.out.println("Handle: " + msg.getPacketId());
        var deserializer = deserializers.get(msg.getPacketId());
        switch (msg.getPacketId()) {
            case RequestRegister.PACKET_ID: {
                RequestRegisterHandler handler = (RequestRegisterHandler) deserializer;
                ResponseRegister receive = handler.handle((RequestRegister) msg);
                ctx.channel().writeAndFlush(receive);
                break;
            }
            case RequestLogin.PACKET_ID: {
                RequestLoginHandler handler = (RequestLoginHandler) deserializer;
                ResponseLogin responseLogin = handler.handle((RequestLogin) msg);
                ctx.channel().writeAndFlush(responseLogin);
            }
        }
    }

}
