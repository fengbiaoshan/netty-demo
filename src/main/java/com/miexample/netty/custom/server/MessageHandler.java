package com.miexample.netty.custom.server;

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
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        System.out.println(msg);

        String content = "Hello, I'm 服务端!";
        Integer length = content.getBytes("utf-8").length;

        Message m = new Message();
        m.setLength(length);
        m.setContent(content);
        ctx.write(m);
        ctx.flush();
    }
}
