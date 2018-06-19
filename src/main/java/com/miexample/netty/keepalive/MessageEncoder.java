package com.miexample.netty.keepalive;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * MessageEncoder
 *
 * @author reck
 * @date 2018/06/19
 */
public class MessageEncoder extends MessageToByteEncoder<ReuseMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ReuseMessage msg, ByteBuf out) throws Exception {
        out.writeByte(msg.getType().ordinal());
        out.writeInt(msg.getLength());
        if (msg.getContent() != null) {
            out.writeBytes(msg.getContent().getBytes("utf-8"));
        }
    }
}
