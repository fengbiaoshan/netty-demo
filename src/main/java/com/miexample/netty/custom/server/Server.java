package com.miexample.netty.custom.server;

import com.miexample.netty.custom.MessageDecoder;
import com.miexample.netty.custom.MessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Server
 *
 * @author reck
 * @date 2018/06/19
 */
public class Server {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                       ch.pipeline()
                               .addLast("decoder1", new MessageDecoder())
                               .addLast("encoder1", new MessageEncoder())
                               .addLast("encoder2", new MessageEncoder())
                               .addLast("handler", new MessageHandler());
                    }
                });
        bootstrap.bind(5674).sync();
    }
}
