package com.miexample.netty.keepalive.server;

import com.miexample.netty.keepalive.MessageDecoder;
import com.miexample.netty.keepalive.MessageEncoder;
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
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast("decoder", new MessageDecoder())
                                .addLast("encoder", new MessageEncoder())
                                .addLast("handler", new MessageHandler());

                    }
                });
        bootstrap.bind(7896).sync();
    }
}
