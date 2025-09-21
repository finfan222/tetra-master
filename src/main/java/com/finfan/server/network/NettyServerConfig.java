package com.finfan.server.network;// NettyServerConfig.java

import com.finfan.server.network.channels.LengthFieldBasedFrameEncoder;
import com.finfan.server.network.channels.PacketDecoder;
import com.finfan.server.network.channels.PacketEncoder;
import com.finfan.server.network.packets.serder.PacketDeserializer;
import com.finfan.server.network.packets.serder.PacketSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.finex.core.utils.GenericUtils;

import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class NettyServerConfig {

    //@Value("${server.channel-host}")
    //private String host;
    @Value("${server.game-port}")
    private int port;

    @Bean
    public Map<Integer, PacketDeserializer> deserializers(List<PacketDeserializer> list) {
        return list.stream().collect(Collectors.toMap(PacketDeserializer::getPacketId, Function.identity()));
    }

    @Bean
    public Map<Class<?>, PacketSerializer> serializers(List<PacketSerializer> list) {
        return list.stream().collect(Collectors.toMap(e -> GenericUtils.getInterfaceGenericType(e.getClass(), PacketSerializer.class, 0), Function.identity()));
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
    public ServerBootstrap serverBootstrap(PacketDecoder packetDecoder, PacketEncoder packetEncoder, GameSessionFactory gameSessionFactory) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        // buffer validate handler
                        //pipeline.addLast(new ReadTimeoutHandler(30000, TimeUnit.MILLISECONDS));
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, Short.MAX_VALUE, 0, 4, 0, 4, false));
                        pipeline.addLast(new LengthFieldBasedFrameEncoder(Short.MAX_VALUE, 4));
                        pipeline.addLast(packetDecoder);
                        pipeline.addLast(packetEncoder);
                        pipeline.addLast(gameSessionFactory.create());
                    }
                })
                .childOption(ChannelOption.SO_BACKLOG, 100)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                //.childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
        ;
        return serverBootstrap;
    }

    @Bean
    public Channel serverChannel(PacketDecoder packetDecoder, PacketEncoder packetEncoder, GameSessionFactory packetHandlerFactory) throws InterruptedException {
        return serverBootstrap(packetDecoder, packetEncoder, packetHandlerFactory).bind(port).sync().channel();
    }
}