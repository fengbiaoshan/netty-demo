package com.miexample.netty.custom.client;

import com.miexample.netty.custom.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * MessageHandler
 *
 * @author reck
 * @date 2018/06/19
 */
public class MessageHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) {
       System.out.println(msg);
    }
}
