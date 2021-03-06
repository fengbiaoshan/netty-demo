package com.miexample.netty.keepalive.client;

import com.miexample.netty.keepalive.AbstractMessageHandler;
import com.miexample.netty.keepalive.ChannelCache;
import com.miexample.netty.keepalive.ReuseMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * MessageHandler
 *
 * @author reck
 * @date 2018/06/19
 */
public class MessageHandler extends AbstractMessageHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ReuseMessage msg) throws Exception {
        Channel ch = ctx.channel();
        Integer chHash = ch.hashCode();

        switch (msg.getType()) {
            case HEARTBEAT:
                System.out.println("recieved heartbeat, cancel close:" + chHash);
                ChannelCache currentCache = cache.get(chHash);
                System.out.println(currentCache);
                ScheduledFuture scheduledFuture = ctx.executor().schedule(
                        () -> ch.close(), 30, TimeUnit.SECONDS);
                currentCache.getScheduledFuture().cancel(true);
                currentCache.setScheduledFuture(scheduledFuture);
                ctx.executor().schedule(
                        () -> {
                        System.out.println("delay finished and send heartbeat:" + chHash);
                        ctx.channel().writeAndFlush(msg);
                    }, 20, TimeUnit.SECONDS);
                break;
            case NORMAL:
                System.out.println(msg);
                break;
            default:
                    throw new IllegalArgumentException();
        }

    }
}
