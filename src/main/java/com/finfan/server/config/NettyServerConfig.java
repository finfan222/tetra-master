package com.finfan.server.config;// NettyServerConfig.java

import com.finfan.server.PacketDeserializer;
import com.finfan.server.channels.PacketDecoder;
import com.finfan.server.channels.PacketEncoder;
import com.finfan.server.channels.PacketHandlerFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class NettyServerConfig {

    @Value("${server.channel}")
    private int port;

    @Bean
    public Map<Integer, PacketDeserializer> deserializers(List<PacketDeserializer> list) {
        return list.stream().collect(Collectors.toMap(PacketDeserializer::getPacketId, Function.identity()));
    }

    @Bean
    public EventLoopGroup bossGroup() {
        return new NioEventLoopGroup(1);
    }

    @Bean
    public EventLoopGroup workerGroup() {
        return new NioEventLoopGroup();
    }

    @Bean
    public ServerBootstrap serverBootstrap(PacketDecoder packetDecoder, PacketEncoder packetEncoder, PacketHandlerFactory packetHandlerFactory) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        // buffer validate handler
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, Short.MAX_VALUE, 0, 4, 0, 4, false));
                        pipeline.addLast(packetDecoder);
                        pipeline.addLast(packetHandlerFactory.create());
                        pipeline.addLast(packetEncoder);
                    }
                });
        return serverBootstrap;
    }

    @Bean
    public Channel serverChannel(PacketDecoder packetDecoder, PacketEncoder packetEncoder, PacketHandlerFactory packetHandlerFactory) throws InterruptedException {
        return serverBootstrap(packetDecoder, packetEncoder, packetHandlerFactory).bind(port).sync().channel();
    }
}