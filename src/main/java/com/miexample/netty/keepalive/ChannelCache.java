package com.miexample.netty.keepalive;

import io.netty.channel.Channel;
import io.netty.util.concurrent.ScheduledFuture;

/**
 * ChannelCache
 *
 * @author reck
 * @date 2018/06/19
 */
public class ChannelCache {

    public ChannelCache(Channel ch, ScheduledFuture scheduledFuture) {
        this.channel = ch;
        this.scheduledFuture = scheduledFuture;
    }

    private Channel channel;

    private ScheduledFuture scheduledFuture;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ScheduledFuture getScheduledFuture() {
        return scheduledFuture;
    }

    public void setScheduledFuture(ScheduledFuture scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }
}
