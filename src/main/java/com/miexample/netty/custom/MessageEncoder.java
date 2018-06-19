package com.miexample.netty.custom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.UnsupportedEncodingException;

/**
 * MessageEncoder
 *
 * @author reck
 * @date 2018/06/19
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws UnsupportedEncodingException {
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent().getBytes("utf-8"));
    }
}
