package com.miexample.netty.keepalive;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * AbstractMessageHandler
 *
 * @author reck
 * @date 2018/06/19
 */
public abstract class AbstractMessageHandler extends SimpleChannelInboundHandler<ReuseMessage> {

    protected static Map<Integer, ChannelCache> cache = new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Channel ch = ctx.channel();
        Integer chHash = ch.hashCode();

        if (!cache.containsKey(chHash)) {
            System.out.println("not cache, put key:" + chHash);
            ch.closeFuture().addListener(future -> {
                System.out.println("channel close, remove key:" + chHash);
                cache.remove(chHash);
            });
            ScheduledFuture scheduledFuture = ctx.executor().schedule(
                    () -> {
                        System.out.println("schedule runs, close channel:" + chHash);
                        ch.close();
                    }, 10, TimeUnit.SECONDS);
            cache.put(chHash, new ChannelCache(ch, scheduledFuture));
        }
    }
}
