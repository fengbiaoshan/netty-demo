package com.miexample.netty.keepalive.client;

import com.miexample.netty.keepalive.MessageDecoder;
import com.miexample.netty.keepalive.MessageEncoder;
import com.miexample.netty.keepalive.ReuseMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * Client
 *
 * @author reck
 * @date 2018/06/19
 */
public class Client {
    public static ChannelGroup allChannels =
         new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast("decoder", new MessageDecoder())
                                .addLast("encoder", new MessageEncoder())
                                .addLast("handler", new MessageHandler());
                    }
                });
        ChannelFuture f = bootstrap.connect(new InetSocketAddress(7896)).sync();
        Channel ch = f.channel();
        Scanner scanner = new Scanner(System.in);
        int code;
        while (ch.isOpen()) {
            code = scanner.nextInt();
            if (code == 0) {
                break;
            } else if (code == 1) {
                ReuseMessage msg = new ReuseMessage();
                msg.setType(ReuseMessage.MessageType.HEARTBEAT);
                msg.setLength(0);
                ch.write(msg);
                ch.flush();
                System.out.println("send heartbeat");
            } else if (code == 2) {
                ReuseMessage msg = new ReuseMessage();
                msg.setType(ReuseMessage.MessageType.NORMAL);
                String content = "hello, I'm 客户端";
                msg.setLength(content.getBytes("utf-8").length);
                msg.setContent(content);
                ch.write(msg);
                ch.flush();
                System.out.println("send message");
            }

        }

        group.shutdownGracefully();

    }
}
