package com.miexample.netty.custom.client;

import com.miexample.netty.custom.Message;
import com.miexample.netty.custom.MessageDecoder;
import com.miexample.netty.custom.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;

/**
 * Client
 *
 * @author reck
 * @date 2018/06/19
 */
public class Client {
    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline()
                               .addLast("decoder1", new MessageDecoder())
                               .addLast("encoder1", new MessageEncoder())
                               .addLast("encoder2", new MessageEncoder())
                               .addLast("handler", new MessageHandler());
                    }
                });
        ChannelFuture f = b.connect(new InetSocketAddress(5674)).sync();
        String content = "Hi, 我是客户端!";
        Integer length = content.getBytes("utf-8").length;
        Message m = new Message();
        m.setLength(length);
        m.setContent(content);
        f.channel().write(m);
        f.channel().flush();
    }
}
