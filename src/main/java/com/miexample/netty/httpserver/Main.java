package com.miexample.netty.httpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;

/**
 * com.miexample.netty.httpserver.Main
 *
 * @author reck
 * @date 2018/06/18
 */
public class Main {
    private static final int port = 3453;

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            //线程池group
            ServerBootstrap b = new ServerBootstrap();
            //设置用于accept的channel，设置accept获得的子channel，并设置其中的handler。
            b.group(group).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                           ch.pipeline()
                                   .addLast("decoder", new HttpRequestDecoder())
                                   .addLast("encoder", new HttpResponseEncoder())
                                   .addLast("aggregator", new HttpObjectAggregator(512*1024))
                                   .addLast("handler", new HttpHandler());
                        }

                    });
            //绑定地址端口并开始接受请求
            b.bind(new InetSocketAddress(port)).sync();
        } catch(Exception e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }


    }
}
